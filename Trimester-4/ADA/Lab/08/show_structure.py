#!/usr/bin/env python3
"""
Script to display the project structure
"""

import os

def display_tree(directory, prefix="", max_depth=3, current_depth=0):
    """Display directory structure as a tree"""
    if current_depth > max_depth:
        return
    
    items = sorted(os.listdir(directory))
    dirs = [item for item in items if os.path.isdir(os.path.join(directory, item)) and not item.startswith('.')]
    files = [item for item in items if os.path.isfile(os.path.join(directory, item)) and not item.startswith('.')]
    
    # Display directories first
    for i, dir_name in enumerate(dirs):
        is_last_dir = (i == len(dirs) - 1) and len(files) == 0
        current_prefix = "└── " if is_last_dir else "├── "
        print(f"{prefix}{current_prefix}📁 {dir_name}/")
        
        next_prefix = prefix + ("    " if is_last_dir else "│   ")
        display_tree(os.path.join(directory, dir_name), next_prefix, max_depth, current_depth + 1)
    
    # Display files
    for i, file_name in enumerate(files):
        is_last = i == len(files) - 1
        current_prefix = "└── " if is_last else "├── "
        
        # Add file type emoji
        if file_name.endswith('.py'):
            emoji = "🐍"
        elif file_name.endswith('.csv'):
            emoji = "📊"
        elif file_name.endswith('.md'):
            emoji = "📝"
        elif file_name.endswith('.txt'):
            emoji = "📄"
        else:
            emoji = "📄"
        
        print(f"{prefix}{current_prefix}{emoji} {file_name}")

if __name__ == "__main__":
    print("📦 Production Time Analysis Dashboard - Project Structure")
    print("=" * 60)
    display_tree(".", max_depth=3)
