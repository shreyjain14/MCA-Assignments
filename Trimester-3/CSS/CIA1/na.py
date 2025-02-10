import networkx as nx
import matplotlib.pyplot as plt

# Create a simple graph
G = nx.Graph()
G.add_edges_from([(1, 2), (2, 3), (3, 4), (4, 1), (2, 4)])

# Draw the graph
nx.draw(G, with_labels=True, node_color="lightblue", edge_color="gray", node_size=1500, font_size=16)
plt.show()
