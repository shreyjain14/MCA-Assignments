"""
Configuration settings for the Streamlit application
"""

import streamlit as st

def setup_page_config():
    """Setup Streamlit page configuration"""
    st.set_page_config(
        page_title="Production Time Analysis Dashboard",
        page_icon="🏭",
        layout="wide",
        initial_sidebar_state="expanded"
    )

# Application constants
APP_TITLE = "Production Time Analysis Dashboard"
DATA_FILE = "production_time_dataset.csv"

# Color scheme
COLORS = {
    "primary_blue": "#1f77b4",
    "primary_orange": "#ff7f0e", 
    "light_orange": "#fff5f0",
    "light_blue": "#f0f8ff",
    "success_green": "#2ca02c",
    "warning_red": "#d62728",
    "background_gray": "#f8f9fa"
}

# Page configuration
PAGES = {
    "🏠 Overview": "overview",
    "📥 Data Collection": "data_collection",
    "🧹 Data Cleaning": "data_cleaning",
    "🔍 EDA": "eda",
    "❓ Missing Data Analysis": "missing_data",
    "🔬 Analysis Techniques": "analysis_techniques",
    "📊 Visualizations": "visualizations",
    "💡 Recommendations": "recommendations",
    "🤖 Predictive Modeling": "predictive_modeling"
}
