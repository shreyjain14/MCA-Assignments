"""
Custom styling and CSS for the Streamlit application
"""

import streamlit as st
from utils.config import COLORS

def apply_custom_css():
    """Apply custom CSS styling to the application"""
    
    st.markdown(f"""
    <style>
        .main-header {{
            font-size: 3rem;
            color: {COLORS['primary_blue']};
            text-align: center;
            margin-bottom: 2rem;
        }}
        .section-header {{
            font-size: 2rem;
            color: {COLORS['primary_orange']};
            margin-top: 2rem;
            margin-bottom: 1rem;
        }}
        .metric-card {{
            background-color: {COLORS['light_orange']};
            padding: 1rem;
            border-radius: 0.5rem;
            border-left: 4px solid {COLORS['primary_orange']};
            margin: 0.5rem 0;
        }}
        .insight-box {{
            background-color: {COLORS['light_blue']};
            padding: 1rem;
            border-radius: 0.5rem;
            border-left: 4px solid {COLORS['primary_blue']};
            margin: 1rem 0;
        }}
        
        /* Custom button styling for navigation */
        .stButton > button {{
            width: 100%;
            border-radius: 8px;
            border: 1px solid {COLORS['primary_orange']};
            background-color: {COLORS['light_orange']};
            color: {COLORS['primary_orange']};
            padding: 0.75rem 1rem;
            margin-bottom: 0.5rem;
            font-weight: 500;
            transition: all 0.3s ease;
        }}
        
        .stButton > button:hover {{
            background-color: {COLORS['primary_orange']};
            color: white;
            border-color: {COLORS['primary_orange']};
            transform: translateX(5px);
            box-shadow: 0 2px 8px rgba(255, 127, 14, 0.3);
        }}
        
        .stButton > button:focus {{
            background-color: {COLORS['primary_orange']};
            color: white;
            border-color: {COLORS['primary_orange']};
            box-shadow: 0 0 0 2px rgba(255, 127, 14, 0.2);
        }}
        
        .stButton > button:active {{
            background-color: #e6720c;
            color: white;
            border-color: #e6720c;
        }}
        
        /* Sidebar styling */
        .css-1d391kg {{
            background-color: #fafafa;
            border-right: 2px solid {COLORS['primary_orange']};
        }}
        
        /* Sidebar title styling */
        .css-1d391kg h1 {{
            color: {COLORS['primary_orange']};
            border-bottom: 2px solid {COLORS['primary_orange']};
            padding-bottom: 0.5rem;
            margin-bottom: 1rem;
        }}
        
        /* Hide the hamburger menu */
        .css-14xtw13.e8zbici0 {{
            display: none;
        }}
        
        /* Recommendation box styling */
        .recommendation-box {{
            background-color: {COLORS['light_orange']};
            padding: 1rem;
            border-radius: 0.5rem;
            border-left: 4px solid {COLORS['primary_orange']};
            margin: 1rem 0;
        }}
        
        /* Success and warning boxes */
        .success-box {{
            background-color: #f0f8f0;
            padding: 1rem;
            border-radius: 0.5rem;
            border-left: 4px solid {COLORS['success_green']};
            margin: 1rem 0;
        }}
        
        .warning-box {{
            background-color: #fff5f5;
            padding: 1rem;
            border-radius: 0.5rem;
            border-left: 4px solid {COLORS['warning_red']};
            margin: 1rem 0;
        }}
    </style>
    """, unsafe_allow_html=True)
