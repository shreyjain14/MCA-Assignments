class Node:
    def __init__(self, key):
        self.left = None
        self.right = None
        self.val = key

def insert_level_order(arr, root, i, n):
    if i < n:
        temp = Node(arr[i])
        root = temp
        root.left = insert_level_order(arr, root.left, 2 * i + 1, n)
        root.right = insert_level_order(arr, root.right, 2 * i + 2, n)
    return root

def in_order(root, result):
    if root:
        in_order(root.left, result)
        result.append(root.val)
        in_order(root.right, result)

def pre_order(root, result):
    if root:
        result.append(root.val)
        pre_order(root.left, result)
        pre_order(root.right, result)

def post_order(root, result):
    if root:
        post_order(root.left, result)
        post_order(root.right, result)
        result.append(root.val)

def level_order(root):
    result = []
    if root is None:
        return result
    queue = []
    queue.append(root)
    while queue:
        node = queue.pop(0)
        result.append(node.val)
        if node.left:
            queue.append(node.left)
        if node.right:
            queue.append(node.right)
    return result

def write_orders_to_file(root):
    in_order_result = []
    pre_order_result = []
    post_order_result = []
    
    in_order(root, in_order_result)
    pre_order(root, pre_order_result)
    post_order(root, post_order_result)
    level_order_result = level_order(root)
    
    with open('order.txt', 'w') as file:
        file.write(' '.join(map(str, in_order_result)) + '\n')
        file.write(' '.join(map(str, pre_order_result)) + '\n')
        file.write(' '.join(map(str, post_order_result)) + '\n')
        file.write(' '.join(map(str, level_order_result)) + '\n')

def main():
    n = int(input("Enter the height of the tree: "))
    num_nodes = 2**n - 1
    arr = list(range(1, num_nodes + 1))
    root = insert_level_order(arr, None, 0, num_nodes)
    write_orders_to_file(root)

if __name__ == "__main__":
    main()