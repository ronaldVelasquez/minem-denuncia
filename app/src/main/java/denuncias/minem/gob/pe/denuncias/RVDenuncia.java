package denuncias.minem.gob.pe.denuncias;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ronaldvelasquez on 7/01/17.
 */

public class RVDenuncia extends RecyclerView.Adapter<RVDenuncia.VHDenuncia> {

    private List<DenunciaEntity> denuncias;

    public RVDenuncia() {
        this.denuncias = new ArrayList<>();
    }

    @Override
    public VHDenuncia onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VHDenuncia(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_denuncia, parent, false));
    }

    public void addDenuncias(List<DenunciaEntity> denuncias) {
        this.denuncias.clear();
        this.denuncias.addAll(denuncias);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(VHDenuncia holder, int position) {
        DenunciaEntity denuncia = denuncias.get(position);
        holder.txtTitle.setText(denuncia.getTitle());
        holder.txtName.setText(denuncia.getPersonName());
        File imgFile = new  File(denuncia.getPhoto());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.circleImagePhoto.setImageBitmap(myBitmap);
        }
    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    class VHDenuncia extends RecyclerView.ViewHolder {
        private CircleImageView circleImagePhoto;
        private TextView txtTitle;
        private TextView txtName;

        public VHDenuncia(View itemView) {
            super(itemView);
            circleImagePhoto = (CircleImageView) itemView.findViewById(R.id.circle_image_photo);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
        }
    }
}
