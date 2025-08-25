"""
Data Collection Analysis page
"""

import streamlit as st
import matplotlib.pyplot as plt
from utils.data_loader import load_data
from utils.config import COLORS

def render_data_collection_page():
    """Render the data collection analysis page"""
    
    st.markdown('<h2 class="section-header">📥 Data Collection Analysis</h2>', unsafe_allow_html=True)
    
    df = load_data()
    if df is None:
        return
    
    # Analysis of data types and sources
    st.markdown(f"""
    <div style="background-color: {COLORS['light_orange']}; padding: 1rem; border-radius: 0.5rem; border-left: 4px solid {COLORS['primary_orange']}; margin: 1rem 0;">
        <h4 style="color: {COLORS['primary_orange']};">🔍 Data Collection Assessment</h4>
        <ul style="color: #333;">
            <li><strong>Data Type:</strong> Real production data from manufacturing operations</li>
            <li><strong>Data Sources:</strong> Manufacturing execution systems, machine logs, operator records</li>
            <li><strong>Collection Method:</strong> Automated data logging with manual operator input</li>
        </ul>
    </div>
    """, unsafe_allow_html=True)
    
    # Data quality analysis
    col1, col2 = st.columns(2)
    
    with col1:
        st.subheader("📊 Data Completeness by Column")
        missing_data = df.isnull().sum().sort_values(ascending=False)
        missing_percent = (missing_data / len(df)) * 100
        
        fig, ax = plt.subplots(figsize=(10, 6))
        bars = ax.bar(missing_percent.index, missing_percent.values)
        plt.xticks(rotation=45, ha='right')
        plt.ylabel('Missing Data (%)')
        plt.title('Missing Data Percentage by Column', color=COLORS['primary_orange'], fontsize=14, fontweight='bold')
        
        # Color bars based on missing percentage using website color scheme
        for i, bar in enumerate(bars):
            if missing_percent.iloc[i] > 10:
                bar.set_color(COLORS['warning_red'])  # Red for high missing
            elif missing_percent.iloc[i] > 5:
                bar.set_color(COLORS['primary_orange'])  # Orange for medium missing
            else:
                bar.set_color(COLORS['success_green'])  # Green for low missing
        
        # Style the plot to match website theme
        ax.set_facecolor(COLORS['background_gray'])
        ax.grid(True, alpha=0.3, color=COLORS['primary_blue'])
        ax.spines['top'].set_color(COLORS['primary_orange'])
        ax.spines['right'].set_color(COLORS['primary_orange'])
        ax.spines['bottom'].set_color(COLORS['primary_orange'])
        ax.spines['left'].set_color(COLORS['primary_orange'])
        
        plt.tight_layout()
        st.pyplot(fig)
    
    with col2:
        st.subheader("🎯 Data Quality Metrics")
        total_cells = len(df) * len(df.columns)
        complete_cells = total_cells - df.isnull().sum().sum()
        completeness = (complete_cells / total_cells) * 100
        
        st.metric("Overall Data Completeness", f"{completeness:.2f}%")
        st.metric("Records with Complete Data", len(df.dropna()))
        st.metric("Records with Any Missing Data", len(df) - len(df.dropna()))
        
        # Recommendations
        st.markdown(f"""
        <div style="background-color: {COLORS['light_orange']}; padding: 1rem; border-radius: 0.5rem; border-left: 4px solid {COLORS['primary_orange']}; margin: 1rem 0;">
        <h4 style="color: {COLORS['primary_orange']};">📋 Data Collection Recommendations</h4>
        <ul style="color: #333;">
        <li>Implement automated data validation at collection points</li>
        <li>Regular system maintenance to prevent data loss</li>
        <li>Backup systems for critical production metrics</li>
        <li>Training for operators on data entry best practices</li>
        </ul>
        </div>
        """, unsafe_allow_html=True)
