import lyricsgenius

# Create a Genius API object
genius = lyricsgenius.Genius("njRWUC5v5Rx7s2i3KRNS3epeEW-KpUXOlrebnIADaYrfnPuvuE0jqjt34gzQwhNO")

while True:
    # Prompt the user to enter a song name
    song_name = input("\n\n\nEnter the name of the song (or 'exit' to quit): ")

    if song_name.lower() == "exit":
        break

    # Search for the lyrics of the song
    song = genius.search_song(song_name)

    # Check if the song was found
    if song is not None:
        # Print the lyrics
        print(song.lyrics)
    else:
        print("Lyrics not found for the given song.")