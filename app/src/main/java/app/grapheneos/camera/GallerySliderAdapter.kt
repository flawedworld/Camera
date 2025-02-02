package app.grapheneos.camera

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import app.grapheneos.camera.capturer.VideoCapturer
import app.grapheneos.camera.ui.activities.InAppGallery
import app.grapheneos.camera.ui.activities.VideoPlayer
import app.grapheneos.camera.ui.fragment.GallerySlide

class GallerySliderAdapter(
    private val gActivity: InAppGallery,
    private val mediaUris: ArrayList<Uri>
) : RecyclerView.Adapter<GallerySlide>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(
        gActivity
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            GallerySlide {
        return GallerySlide(
            layoutInflater.inflate(
                R.layout.gallery_slide,
                parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return mediaUris[position].hashCode().toLong()
    }

    override fun onBindViewHolder(holder: GallerySlide, position: Int) {

        val mediaPreview: ImageView =
            holder.itemView.findViewById(R.id.slide_preview)

        val mediaUri = mediaUris[position]

        val playButton: ImageView =
            holder.itemView.findViewById(R.id.play_button)

        val rootView: View =
            holder.itemView.findViewById(R.id.root)

        if (VideoCapturer.isVideo(mediaUri)) {
            try {
                mediaPreview.setImageBitmap(
                    CamConfig.getVideoThumbnail(
                        gActivity,
                        mediaUri
                    )
                )

                playButton.visibility = View.VISIBLE

                rootView.setOnClickListener {

                    val mUri = getCurrentUri()

                    if (VideoCapturer.isVideo(mUri)) {
                        val intent = Intent(
                            gActivity,
                            VideoPlayer::class.java
                        )
                        intent.putExtra(
                            "videoUri", mUri)

                        gActivity.startActivity(intent)
                    }
                }

            } catch (exception: Exception) {
            }

        } else {
            playButton.visibility = View.INVISIBLE
            rootView.setOnClickListener(null)
            mediaPreview.setImageURI(mediaUri)
        }
    }

    fun removeUri(uri: Uri) {
        removeChildAt(mediaUris.indexOf(uri))
    }

    private fun removeChildAt(index: Int) {
        mediaUris.removeAt(index)

        // Close gallery if no files are present
        if (mediaUris.isEmpty()) {
            gActivity.showMessage(
                "No image found. Exiting in-app gallery."
            )
            gActivity.finish()
        }

        notifyItemRemoved(index)
    }

    fun getCurrentUri(): Uri {
        return mediaUris[gActivity.gallerySlider.currentItem]
    }

    override fun getItemCount(): Int {
        return mediaUris.size
    }
}