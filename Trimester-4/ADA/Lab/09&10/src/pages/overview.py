"""
Overview page for the Production Time Analysis Dashboard
"""

import streamlit as st
import pandas as pd
from utils.data_loader import load_data, get_data_summary

def render_overview_page():
    """Render the overview page"""
    
    st.markdown('<h1 class="main-header">🏭 Production Time Analysis Dashboard</h1>', unsafe_allow_html=True)
    
    df = load_data()
    if df is None:
        return
    
    st.markdown('<h2 class="section-header">📊 Data Overview</h2>', unsafe_allow_html=True)
    
    # Get data summary
    summary = get_data_summary(df)
    
    # Basic metrics
    col1, col2, col3, col4 = st.columns(4)
    with col1:
        st.metric("Total Records", summary['total_records'])
    with col2:
        st.metric("Total Columns", summary['total_columns'])
    with col3:
        st.metric("Missing Values", summary['missing_values'])
    with col4:
        st.metric("Duplicate Records", summary['duplicate_records'])
    
    # Dataset sample
    st.subheader("📋 Dataset Sample")
    st.dataframe(df.head(10))
    
    # Data types and info
    col1, col2 = st.columns(2)
    
    with col1:
        st.subheader("📈 Data Types")
        data_types = pd.DataFrame({
            'Column': df.columns,
            'Data Type': df.dtypes.astype(str),
            'Non-Null Count': df.count().values,
            'Null Count': df.isnull().sum().values
        })
        st.dataframe(data_types)
    
    with col2:
        st.subheader("📊 Basic Statistics")
        st.dataframe(df.describe())
