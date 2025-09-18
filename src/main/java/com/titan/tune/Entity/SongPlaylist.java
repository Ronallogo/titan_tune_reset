package com.titan.tune.Entity;


import com.titan.tune.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.util.UUID;

@Entity
@Table(name="SONG_PLAYLISTS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongPlaylist   extends BaseEntity {

    @Id
    @EmbeddedId
    private  SongPlaylist_id songPlaylistId ;


    @Column(name = "tracking_id" , nullable = false , updatable = false)
    private UUID trackingId;


    @Column(name = "is_inside"  , nullable = false)

    private boolean isInside ;

    @ManyToOne
    @JoinColumn(name="song_id" , nullable = false , updatable = false)
    private Song song ;

    @ManyToOne
    @JoinColumn(name = "playlist_id" , nullable = false , updatable = false)
    private Playlist playlist ;

    public SongPlaylist(SongPlaylist_id songPlaylistId, Song song, Playlist playlist) {
        this.setPlaylist(playlist);
        this.setSongPlaylistId(songPlaylistId);
        this.setSong(song);
    }
}
