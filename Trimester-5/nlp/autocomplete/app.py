import streamlit as st
import pandas as pd
import os
import random

# Set page config
st.set_page_config(page_title="Harry Potter Story Generator", layout="wide")

# Title
st.title("📚 Harry Potter Story Generator")
st.markdown("Generate stories based on n-gram probability analysis of the Harry Potter series")

# Load n-grams CSV
@st.cache_data
def load_ngrams(n_grams_file='data/n_grams/n_grams_3.csv'):
    return pd.read_csv(n_grams_file)

df = load_ngrams()

def generate_story(starting_words, length=100):
    """Generate a story starting from two words using n-gram model."""
    words = starting_words.split()
    if len(words) != 2:
        return "Error: Please provide exactly 2 starting words"
    
    story = words.copy()
    
    while len(story) < length:
        first_word = story[-2]
        second_word = story[-1]
        
        matching = df[(df['word_1'] == first_word) & (df['word_2'] == second_word)].copy()
        
        if len(matching) > 0:
            total_count = matching['count'].sum()
            matching['probability'] = matching['count'] / total_count
            
            next_word = random.choices(matching['word_3'].values, weights=matching['probability'].values)[0]
            story.append(next_word)
        else:
            random_word = df.sample(1)['word_3'].values[0]
            story.append(random_word)
    
    return ' '.join(story)

def show_next_word_options(first_word, second_word):
    """Show top next word options after given two words."""
    matching = df[(df['word_1'] == first_word) & (df['word_2'] == second_word)].copy()
    
    if len(matching) == 0:
        return None
    
    total_count = matching['count'].sum()
    matching['probability'] = (matching['count'] / total_count * 100).round(2)
    
    return matching.sort_values('probability', ascending=False)[['word_3', 'count', 'probability']].head(10)

# Sidebar for navigation
st.sidebar.title("Navigation")
mode = st.sidebar.radio("Select Mode:", ["Story Generator", "Word Prediction", "Statistics"])

if mode == "Story Generator":
    st.header("📖 Generate a Story")
    
    col1, col2 = st.columns([2, 1])
    
    with col1:
        starting_words = st.text_input(
            "Enter 2 starting words (space-separated):",
            value="Harry Potter",
            help="Example: 'Harry Potter', 'The Wizard', etc."
        )
    
    with col2:
        story_length = st.slider("Story length (words):", min_value=50, max_value=2000, value=300, step=50)
    
    if st.button("🎬 Generate Story", use_container_width=True):
        if len(starting_words.split()) != 2:
            st.error("❌ Please enter exactly 2 words separated by a space")
        else:
            with st.spinner("Generating your story..."):
                story = generate_story(starting_words, length=story_length)
                st.success("✅ Story generated!")
                st.text_area("Generated Story:", value=story, height=300, disabled=True)
                
                # Display stats
                word_count = len(story.split())
                st.caption(f"📊 Total words: {word_count}")

elif mode == "Word Prediction":
    st.header("🔮 Next Word Prediction")
    
    col1, col2 = st.columns(2)
    
    with col1:
        first_word = st.text_input("First word:", value="Harry")
    
    with col2:
        second_word = st.text_input("Second word:", value="Potter")
    
    if st.button("📊 Show Next Word Options", use_container_width=True):
        if not first_word or not second_word:
            st.error("❌ Please enter both words")
        else:
            options = show_next_word_options(first_word, second_word)
            
            if options is None:
                st.warning(f"⚠️ No n-grams found for '{first_word} {second_word}'")
            else:
                st.success(f"✅ Found {len(options)} possible next words")
                
                # Display as table
                display_df = options.copy()
                display_df.columns = ["Next Word", "Count", "Probability (%)"]
                st.dataframe(display_df, use_container_width=True, hide_index=True)
                
                # Display as bar chart
                st.bar_chart(options.set_index('word_3')['probability'])

elif mode == "Statistics":
    st.header("📈 Dataset Statistics")
    
    col1, col2, col3 = st.columns(3)
    
    with col1:
        st.metric("Total N-grams", len(df))
    
    with col2:
        st.metric("Total Occurrences", df['count'].sum())
    
    with col3:
        st.metric("Avg Occurrences", df['count'].mean().round(2))
    
    st.subheader("Most Common N-grams")
    top_ngrams = df.nlargest(20, 'count')[['word_1', 'word_2', 'word_3', 'count']]
    st.dataframe(top_ngrams, use_container_width=True, hide_index=True)
    
    st.subheader("Distribution Analysis")
    col1, col2 = st.columns(2)
    
    with col1:
        st.bar_chart(df.nlargest(15, 'count')['count'].values)
    
    with col2:
        st.write("Count Statistics:")
        st.write(df['count'].describe())

st.markdown("---")
st.markdown("💡 **Built with Streamlit** | Data source: Harry Potter series n-gram analysis")
