import requests
import json

def generate_response(prompt, model="llama3.2", stream=False):
    url = "http://localhost:11434/api/generate"
    
    payload = {
        "model": model,
        "prompt": prompt,
        "stream": stream
    }
    
    print(f"Sending request to {url}")
    print(f"Payload: {json.dumps(payload, indent=2)}")
    
    try:
        # Send the request
        response = requests.post(url, json=payload)
        
        # Print raw response for debugging
        print(f"Response Status Code: {response.status_code}")
        print(f"Response Headers: {response.headers}")
        
        # Check if the request was successful
        response.raise_for_status()
        
        # Parse the response
        result = response.json()
        print("Full Response:", json.dumps(result, indent=2))
        
        return result['response']
    
    except requests.RequestException as e:
        print(f"Error occurred: {e}")
        if hasattr(e, 'response'):
            print(f"Response content: {e.response.text}")
        return None

# Test the function
if __name__ == "__main__":
    response = generate_response("Why is the sky blue?")
    print("\nResponse:", response)