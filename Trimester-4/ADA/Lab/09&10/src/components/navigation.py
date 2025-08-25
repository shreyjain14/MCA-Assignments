"""
Navigation component for the Streamlit application
"""

import streamlit as st
from utils.config import PAGES

# Import all page modules
from pages.overview import render_overview_page
from pages.data_collection import render_data_collection_page
from pages.data_cleaning import render_data_cleaning_page
from pages.eda import render_eda_page
from pages.missing_data import render_missing_data_page
from pages.analysis_techniques import render_analysis_techniques_page
from pages.visualizations import render_visualizations_page
from pages.recommendations import render_recommendations_page
from pages.predictive_modeling import render_predictive_modeling_page

def setup_navigation():
    """Setup navigation system and render the selected page"""
    
    # Initialize session state for page navigation
    if 'current_page' not in st.session_state:
        st.session_state.current_page = "🏠 Overview"
    
    # Sidebar navigation
    st.sidebar.title("🏭 Navigation")
    
    # Create navigation buttons
    for page_name in PAGES.keys():
        if st.sidebar.button(page_name, key=f"btn_{page_name}", use_container_width=True):
            st.session_state.current_page = page_name

    # Route to the selected page
    route_to_page(st.session_state.current_page)


def route_to_page(page_name):
    """Route to the appropriate page based on selection"""
    
    page_functions = {
        "🏠 Overview": render_overview_page,
        "📥 Data Collection": render_data_collection_page,
        "🧹 Data Cleaning": render_data_cleaning_page,
        "🔍 EDA": render_eda_page,
        "❓ Missing Data Analysis": render_missing_data_page,
        "🔬 Analysis Techniques": render_analysis_techniques_page,
        "📊 Visualizations": render_visualizations_page,
        "💡 Recommendations": render_recommendations_page,
        "🤖 Predictive Modeling": render_predictive_modeling_page
    }
    
    # Render the selected page
    if page_name in page_functions:
        page_functions[page_name]()
    else:
        st.error(f"Page '{page_name}' not found!")
