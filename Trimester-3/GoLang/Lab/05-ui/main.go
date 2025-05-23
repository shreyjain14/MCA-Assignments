package main

import (
	"fmt"
	"html/template"
	"log"
	"math"
	"math/rand"
	"net/http"
	"strconv"
	"strings"
	"time"
)

type Node struct {
	Activation func(float64) float64
}

type Layer struct {
	Nodes   []Node
	Weights [][]float64
	Biases  []float64
}

type NeuralNetwork struct {
	Layers []Layer
}

var rng = rand.New(rand.NewSource(time.Now().UnixNano()))

var activations = map[string]func(float64) float64{
	"sigmoid": func(x float64) float64 { return 1.0 / (1.0 + math.Exp(-x)) },
	"step": func(x float64) float64 {
		if x < 0 {
			return 0
		}
		return 1
	},
	"identity": func(x float64) float64 { return x },
}

func NewLayer(nodeCount int, activation func(float64) float64, previousCount int) Layer {
	layer := Layer{}
	layer.Nodes = make([]Node, nodeCount)
	for i := range layer.Nodes {
		layer.Nodes[i] = Node{
			Activation: activation,
		}
	}

	if previousCount > 0 {
		layer.Weights = make([][]float64, previousCount)
		for i := 0; i < previousCount; i++ {
			layer.Weights[i] = make([]float64, nodeCount)
			for j := 0; j < nodeCount; j++ {
				layer.Weights[i][j] = rng.Float64()*2 - 1
			}
		}
		layer.Biases = make([]float64, nodeCount)
		for j := 0; j < nodeCount; j++ {
			layer.Biases[j] = rng.Float64()*2 - 1
		}
	}

	return layer
}

func NewNeuralNetwork(inputCount int, hiddenLayers []int, outputCount int, activationHidden func(float64) float64, activationOutput func(float64) float64) NeuralNetwork {
	var layers []Layer

	inputLayer := NewLayer(inputCount, func(x float64) float64 { return x }, 0)
	layers = append(layers, inputLayer)
	previousCount := inputCount

	for _, count := range hiddenLayers {
		layer := NewLayer(count, activationHidden, previousCount)
		layers = append(layers, layer)
		previousCount = count
	}

	outputLayer := NewLayer(outputCount, activationOutput, previousCount)
	layers = append(layers, outputLayer)

	return NeuralNetwork{
		Layers: layers,
	}
}

func (nn *NeuralNetwork) FeedForward(input []float64) ([]float64, error) {
	if len(input) != len(nn.Layers[0].Nodes) {
		return nil, fmt.Errorf("input size %d does not match input layer size %d", len(input), len(nn.Layers[0].Nodes))
	}
	layerOutput := input

	for l := 1; l < len(nn.Layers); l++ {
		layer := &nn.Layers[l]
		prevOutput := layerOutput
		layerOutput = make([]float64, len(layer.Nodes))
		for j := range layer.Nodes {
			sum := layer.Biases[j]
			for i, val := range prevOutput {
				sum += val * layer.Weights[i][j]
			}
			layerOutput[j] = layer.Nodes[j].Activation(sum)
		}
	}
	return layerOutput, nil
}

func main() {
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		tmpl, err := template.ParseFiles("index.html")
		if err != nil {
			http.Error(w, "Template error", http.StatusInternalServerError)
			return
		}
		tmpl.Execute(w, nil)
	})

	http.HandleFunc("/run", func(w http.ResponseWriter, r *http.Request) {
		if err := r.ParseForm(); err != nil {
			http.Error(w, "Form parse error", http.StatusBadRequest)
			return
		}
		nodesStr := r.FormValue("nodes")
		hiddenAct := r.FormValue("hidden_activation")
		outputAct := r.FormValue("output_activation")

		nodeStrs := strings.Split(nodesStr, ",")
		var nodes []int
		for _, s := range nodeStrs {
			s = strings.TrimSpace(s)
			if n, err := strconv.Atoi(s); err == nil {
				nodes = append(nodes, n)
			}
		}
		if len(nodes) < 2 {
			http.Error(w, "At least two layers required", http.StatusBadRequest)
			return
		}
		inputCount := nodes[0]
		outputCount := nodes[len(nodes)-1]
		var hiddenLayers []int
		if len(nodes) > 2 {
			hiddenLayers = nodes[1 : len(nodes)-1]
		}

		actHidden := activations[hiddenAct]
		actOutput := activations[outputAct]

		nn := NewNeuralNetwork(inputCount, hiddenLayers, outputCount, actHidden, actOutput)

		var input []float64
		input = make([]float64, inputCount)
		for i := range input {
			input[i] = rng.Float64()*2 - 1
		}

		output, err := nn.FeedForward(input)
		if err != nil {
			http.Error(w, fmt.Sprintf("Error: %v", err), http.StatusBadRequest)
			return
		}
		fmt.Fprintf(w, "Output: %v", output)
	})

	log.Println("Server running at http://localhost:8080")
	log.Fatal(http.ListenAndServe(":8080", nil))
}
