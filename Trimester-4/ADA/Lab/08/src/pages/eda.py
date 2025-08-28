"""
Exploratory Data Analysis page
"""

import streamlit as st
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import plotly.express as px
from utils.data_loader import load_data, clean_data

def render_eda_page():
    """Render the exploratory data analysis page"""
    
    st.markdown('<h2 class="section-header">🔍 Exploratory Data Analysis</h2>', unsafe_allow_html=True)
    
    df = load_data()
    if df is None:
        return
    
    # Clean the data first
    df_cleaned = clean_data(df)
    
    # Distribution Analysis
    st.subheader("📈 Distribution Analysis")
    
    col1, col2 = st.columns(2)
    
    with col1:
        # Defects distribution
        fig, ax = plt.subplots(figsize=(10, 6))
        df_cleaned['Defects'].hist(bins=20, alpha=0.7, color='skyblue', edgecolor='black')
        plt.title('Distribution of Defects')
        plt.xlabel('Number of Defects')
        plt.ylabel('Frequency')
        st.pyplot(fig)
    
    with col2:
        # Downtime distribution
        fig, ax = plt.subplots(figsize=(10, 6))
        df_cleaned['Downtime_Minutes'].hist(bins=20, alpha=0.7, color='lightcoral', edgecolor='black')
        plt.title('Distribution of Downtime Minutes')
        plt.xlabel('Downtime (Minutes)')
        plt.ylabel('Frequency')
        st.pyplot(fig)
    
    # Categorical Analysis
    st.subheader("📊 Categorical Analysis")
    
    col1, col2 = st.columns(2)
    
    with col1:
        # Product Type distribution
        fig = px.pie(df_cleaned, names='Product_Type', title='Product Type Distribution')
        st.plotly_chart(fig, use_container_width=True)
    
    with col2:
        # Machine usage
        machine_counts = df_cleaned['Machine_Used'].value_counts()
        fig = px.bar(x=machine_counts.index, y=machine_counts.values, 
                     title='Machine Usage Distribution')
        fig.update_layout(xaxis_title='Machine', yaxis_title='Count')
        st.plotly_chart(fig, use_container_width=True)
    
    # Correlation Analysis
    st.subheader("🔗 Correlation Analysis")
    
    # Select numerical columns for correlation
    numeric_cols = df_cleaned.select_dtypes(include=[np.number]).columns
    if len(numeric_cols) > 1:
        corr_matrix = df_cleaned[numeric_cols].corr()
        
        fig, ax = plt.subplots(figsize=(12, 8))
        sns.heatmap(corr_matrix, annot=True, cmap='coolwarm', center=0, 
                    square=True, linewidths=0.5)
        plt.title('Correlation Matrix of Numerical Variables')
        st.pyplot(fig)
    
    # Time Analysis
    st.subheader("⏰ Time Analysis")
    
    col1, col2 = st.columns(2)
    
    with col1:
        # Shift analysis
        shift_defects = df_cleaned.groupby('Shift')['Defects'].agg(['mean', 'sum', 'count'])
        fig = px.bar(x=shift_defects.index, y=shift_defects['mean'], 
                     title='Average Defects by Shift')
        st.plotly_chart(fig, use_container_width=True)
    
    with col2:
        # Estimated vs Actual time
        fig = px.scatter(df_cleaned, x='Estimated_Time', y='Actual_Time_Taken',
                         color='Product_Type', title='Estimated vs Actual Time')
        fig.add_shape(type="line", x0=0, y0=0, x1=df_cleaned['Estimated_Time'].max(), 
                      y1=df_cleaned['Estimated_Time'].max(), 
                      line=dict(color="red", dash="dash"))
        st.plotly_chart(fig, use_container_width=True)
