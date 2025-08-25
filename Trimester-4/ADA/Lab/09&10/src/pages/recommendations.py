"""
Recommendations and Insights page
"""

import streamlit as st
from utils.data_loader import load_data, clean_data

def render_recommendations_page():
    """Render the recommendations and insights page"""
    
    st.markdown('<h2 class="section-header">💡 Recommendations & Insights</h2>', unsafe_allow_html=True)
    
    df = load_data()
    if df is None:
        return
    
    # Clean the data for analysis
    df_cleaned = clean_data(df)
    
    # Key Performance Insights
    st.subheader("🎯 Key Performance Insights")
    
    # Calculate key metrics
    avg_defects = df_cleaned['Defects'].mean()
    avg_downtime = df_cleaned['Downtime_Minutes'].mean()
    efficiency = (df_cleaned['Estimated_Time'].sum() / df_cleaned['Actual_Time_Taken'].sum()) * 100
    
    # Best/worst performers
    best_machine = df_cleaned.groupby('Machine_Used')['Defects'].mean().idxmin()
    worst_machine = df_cleaned.groupby('Machine_Used')['Defects'].mean().idxmax()
    best_shift = df_cleaned.groupby('Shift')['Defects'].mean().idxmin()
    
    col1, col2, col3 = st.columns(3)
    
    with col1:
        st.metric("Average Defects", f"{avg_defects:.2f}")
        st.metric("Best Machine", best_machine)
    
    with col2:
        st.metric("Average Downtime", f"{avg_downtime:.1f} min")
        st.metric("Best Shift", best_shift)
    
    with col3:
        st.metric("Overall Efficiency", f"{efficiency:.1f}%")
        st.metric("Worst Machine", worst_machine)
    
    # Detailed Recommendations
    st.subheader("📋 Actionable Recommendations")
    
    # Operational Recommendations
    st.markdown("**🔧 Operational Improvements:**")
    
    operational_recs = [
        f"**Prioritize {best_machine} maintenance:** This machine shows lowest defect rates - maintain its performance standards",
        f"**Investigate {worst_machine} issues:** Higher defect rates suggest need for calibration or replacement",
        f"**Optimize {best_shift} shift practices:** Apply successful practices from this shift to others",
        "**Implement predictive maintenance:** Use downtime patterns to predict equipment failures",
        "**Standardize quality procedures:** Reduce variation across different operators and machines"
    ]
    
    for rec in operational_recs:
        st.write(f"• {rec}")
    
    # Quality Improvements
    st.markdown("**📊 Quality Management:**")
    
    quality_recs = [
        "**Set defect rate targets:** Establish maximum acceptable defect rates per product type",
        "**Implement real-time monitoring:** Use IoT sensors for continuous quality tracking",
        "**Root cause analysis:** Investigate orders with >10 defects to identify common factors",
        "**Operator training programs:** Focus on high-defect scenarios and prevention techniques",
        "**Quality control checkpoints:** Add intermediate checks during production process"
    ]
    
    for rec in quality_recs:
        st.write(f"• {rec}")
    
    # Cost-Benefit Analysis
    st.subheader("💰 Cost-Benefit Analysis")
    
    # Calculate potential savings
    total_defects = df_cleaned['Defects'].sum()
    total_downtime = df_cleaned['Downtime_Minutes'].sum()
    
    # Estimate costs (these would be real values in practice)
    defect_cost_per_unit = 50  # Example cost
    downtime_cost_per_minute = 100  # Example cost
    
    current_defect_cost = total_defects * defect_cost_per_unit
    current_downtime_cost = total_downtime * downtime_cost_per_minute
    
    col1, col2 = st.columns(2)
    
    with col1:
        st.markdown("**Current Costs (Estimated):**")
        st.metric("Defect Costs", f"${current_defect_cost:,}")
        st.metric("Downtime Costs", f"${current_downtime_cost:,}")
        st.metric("Total Quality Costs", f"${current_defect_cost + current_downtime_cost:,}")
    
    with col2:
        st.markdown("**Potential Savings (20% improvement):**")
        defect_savings = current_defect_cost * 0.2
        downtime_savings = current_downtime_cost * 0.2
        st.metric("Defect Reduction Savings", f"${defect_savings:,}")
        st.metric("Downtime Reduction Savings", f"${downtime_savings:,}")
        st.metric("Total Annual Savings", f"${(defect_savings + downtime_savings):,}")
    
    # Implementation Feasibility
    st.subheader("✅ Implementation Feasibility")
    
    feasibility_assessment = {
        "Quick Wins (0-3 months)": [
            "Operator training on best practices",
            "Daily quality check protocols",
            "Performance dashboard implementation",
            "Shift handover standardization"
        ],
        "Medium-term (3-12 months)": [
            "Predictive maintenance system",
            "Equipment calibration program",
            "Advanced analytics implementation",
            "Process optimization initiatives"
        ],
        "Long-term (1+ years)": [
            "Equipment replacement/upgrade",
            "Automated quality control systems",
            "Full digital transformation",
            "Supply chain optimization"
        ]
    }
    
    for timeframe, actions in feasibility_assessment.items():
        st.markdown(f"**{timeframe}:**")
        for action in actions:
            st.write(f"• {action}")
    
    # Risk Assessment
    st.subheader("⚠️ Risk Assessment")
    
    risk_factors = [
        "**Implementation Risk:** Medium - requires change management and training",
        "**Financial Risk:** Low - improvements show clear ROI within 6 months",
        "**Operational Risk:** Medium - some disruption during system implementation",
        "**Technology Risk:** Low - using proven technologies and methods",
        "**Market Risk:** Low - improvements enhance competitiveness"
    ]
    
    for risk in risk_factors:
        st.write(f"• {risk}")
