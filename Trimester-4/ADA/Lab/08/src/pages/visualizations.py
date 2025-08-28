"""
Data Visualizations page
"""

import streamlit as st
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import plotly.express as px
import plotly.graph_objects as go
from plotly.subplots import make_subplots
from utils.data_loader import load_data, clean_data

def render_visualizations_page():
    """Render the comprehensive data visualization page"""
    
    st.markdown('<h2 class="section-header">📊 Data Visualization & Presentation</h2>', unsafe_allow_html=True)
    
    df = load_data()
    if df is None:
        return
    
    # Clean the data
    df_cleaned = clean_data(df)
    
    # Visualization Tools Overview
    st.subheader("🛠️ Visualization Tools & Techniques")
    
    tools_info = {
        "Plotly": "Interactive charts for better user engagement and exploration",
        "Matplotlib": "Publication-quality static plots for detailed analysis",
        "Seaborn": "Statistical visualization with beautiful default styles",
        "Streamlit": "Web-based dashboard for real-time data exploration"
    }
    
    for tool, description in tools_info.items():
        st.write(f"**{tool}:** {description}")
    
    # Multiple Chart Types
    st.subheader("📈 Multiple Visualization Types")
    
    # 1. Distribution Charts
    st.markdown("**1. Distribution Analysis:**")
    
    col1, col2 = st.columns(2)
    
    with col1:
        # Box plot for defects by product type
        fig = px.box(df_cleaned, x='Product_Type', y='Defects', 
                     title='Defects Distribution by Product Type')
        st.plotly_chart(fig, use_container_width=True)
    
    with col2:
        # Violin plot for downtime by shift
        fig = px.violin(df_cleaned, x='Shift', y='Downtime_Minutes',
                        title='Downtime Distribution by Shift')
        st.plotly_chart(fig, use_container_width=True)
    
    # 2. Relationship Charts
    st.markdown("**2. Relationship Analysis:**")
    
    col1, col2 = st.columns(2)
    
    with col1:
        # Scatter plot with trend line
        fig = px.scatter(df_cleaned, x='Downtime_Minutes', y='Defects',
                         color='Machine_Used', size='Quantity',
                         trendline='ols', title='Downtime vs Defects Relationship')
        st.plotly_chart(fig, use_container_width=True)
    
    with col2:
        # 3D scatter plot
        fig = px.scatter_3d(df_cleaned, x='Quantity', y='Downtime_Minutes', z='Defects',
                           color='Product_Type', title='3D Production Analysis')
        st.plotly_chart(fig, use_container_width=True)
    
    # 3. Time Series Analysis
    st.markdown("**3. Time Series Visualization:**")
    
    # Create time-based index
    df_cleaned['Order_Sequence'] = df_cleaned['Order_ID'].str.extract(r'(\d+)').astype(int)
    df_cleaned = df_cleaned.sort_values('Order_Sequence')
    
    # Multi-line time series
    fig = make_subplots(
        rows=2, cols=1,
        subplot_titles=('Defects Over Time', 'Downtime Over Time'),
        vertical_spacing=0.1
    )
    
    # Add defects trace
    fig.add_trace(
        go.Scatter(x=df_cleaned['Order_Sequence'], y=df_cleaned['Defects'],
                   mode='lines+markers', name='Defects', line=dict(color='red')),
        row=1, col=1
    )
    
    # Add downtime trace
    fig.add_trace(
        go.Scatter(x=df_cleaned['Order_Sequence'], y=df_cleaned['Downtime_Minutes'],
                   mode='lines+markers', name='Downtime', line=dict(color='blue')),
        row=2, col=1
    )
    
    fig.update_layout(height=600, title_text="Production Metrics Over Time")
    st.plotly_chart(fig, use_container_width=True)
    
    # 4. Advanced Visualizations
    st.markdown("**4. Advanced Visualizations:**")
    
    col1, col2 = st.columns(2)
    
    with col1:
        # Heatmap of performance metrics
        pivot_data = df_cleaned.pivot_table(values='Defects', 
                                           index='Shift', 
                                           columns='Machine_Used', 
                                           aggfunc='mean')
        
        fig, ax = plt.subplots(figsize=(8, 6))
        sns.heatmap(pivot_data, annot=True, cmap='YlOrRd', fmt='.1f')
        plt.title('Average Defects by Shift and Machine')
        st.pyplot(fig)
    
    with col2:
        # Parallel coordinates plot
        fig = px.parallel_coordinates(df_cleaned.sample(100), 
                                    dimensions=['Quantity', 'Downtime_Minutes', 'Defects'],
                                    color='Defects', 
                                    title='Production Parameters Relationships')
        st.plotly_chart(fig, use_container_width=True)
    
    # 5. Statistical Visualizations
    st.markdown("**5. Statistical Insights:**")
    
    col1, col2 = st.columns(2)
    
    with col1:
        # Regression plot
        fig, ax = plt.subplots(figsize=(8, 6))
        sns.regplot(data=df_cleaned, x='Downtime_Minutes', y='Defects', ax=ax)
        plt.title('Downtime vs Defects - Linear Relationship')
        st.pyplot(fig)
    
    with col2:
        # Distribution comparison
        fig, ax = plt.subplots(figsize=(8, 6))
        for product in df_cleaned['Product_Type'].unique():
            if pd.notna(product):
                data = df_cleaned[df_cleaned['Product_Type'] == product]['Defects']
                ax.hist(data, alpha=0.6, label=f'Product {product}', bins=15)
        ax.set_xlabel('Defects')
        ax.set_ylabel('Frequency')
        ax.set_title('Defects Distribution by Product Type')
        ax.legend()
        st.pyplot(fig)
