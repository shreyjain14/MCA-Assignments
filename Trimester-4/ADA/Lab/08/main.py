"""
Main Streamlit Application for Production Time Analysis Dashboard
"""

import streamlit as st
import sys
import os

# Add src directory to path
sys.path.append(os.path.join(os.path.dirname(__file__), 'src'))

from components.navigation import setup_navigation
from components.styling import apply_custom_css
from utils.config import setup_page_config

def main():
    """Main application function"""
    
    # Setup page configuration
    setup_page_config()
    
    # Apply custom CSS styling
    apply_custom_css()
    
    # Setup navigation and run selected page
    setup_navigation()

if __name__ == "__main__":
    main()
