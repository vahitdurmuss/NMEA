package com.example.vahitdurmus.nmeaproject.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vahitdurmus.nmeaproject.R;
import com.example.vahitdurmus.nmeaproject.nmeamessage.GGA;

import java.util.List;

/**
 * Created by vahit.durmus on 19.09.2019.
 */

public class RecyleLocationAdapter extends RecyclerView.Adapter<RecyleLocationAdapter.ViewHolder> {

    private  List<GGA> ggaList;
    private  LayoutInflater layoutInflater;
    private Context context;

    public RecyleLocationAdapter(Context context, List<GGA> ggaList) {
        this.ggaList=ggaList;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyler_row, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        GGA gga = ggaList.get(position);
        holder.latitudeText.setText(String.valueOf(gga.getLatitude()));
        holder.longitudeText.setText(String.valueOf(gga.getLongitude()));
        holder.qualityText.setText(getLocationQuality(gga.getFixQuality()));
        holder.satellitesNumberText.setText(String.valueOf(gga.getNumberOfSatellitesInUse()));
        holder.timeText.setText(gga.getTime());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context).setTitle("INFO").setMessage("want to remove this location?").setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteLocation(ggaList.get(position));
                    }
                }).show().show();
            }
        });
    }
    public void addNLocation(GGA gga){
        ggaList.add(gga);
        notifyDataSetChanged();
    }
    public void deleteLocation(GGA gga){
        ggaList.remove(gga);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return ggaList.size();
    }
    public   class  ViewHolder extends RecyclerView.ViewHolder{

        TextView latitudeText;
        TextView longitudeText;
        TextView qualityText;
        TextView satellitesNumberText;
        TextView timeText;
        ImageButton deleteButton;


        public ViewHolder(View itemView) {
            super(itemView);
            latitudeText = (TextView) itemView.findViewById(R.id.txtview_latitude);
            longitudeText = (TextView) itemView.findViewById(R.id.txtview_longitude);
            qualityText = (TextView) itemView.findViewById(R.id.txtview_quality);
            satellitesNumberText = (TextView) itemView.findViewById(R.id.txtview_satellitesnumber);
            timeText=(TextView) itemView.findViewById(R.id.txtview_time);
            deleteButton=(ImageButton) itemView.findViewById(R.id.buttondelete);
        }
    }

    private String getLocationQuality(int qualityCode){

        StringBuilder stringBuilderQuality=new StringBuilder();
        switch (qualityCode){

            case 0:
                stringBuilderQuality.append("invalid");
                break;
            case 1:
                stringBuilderQuality.append("GPS fix");
                break;
            case  2:
                stringBuilderQuality.append("DGPS fix");
                break;
            case 3:
                stringBuilderQuality.append("PPS fix");
                break;
            case 4:
                stringBuilderQuality.append("Real Time Kinematic");
                break;
            case 5:
                stringBuilderQuality.append("Float RTK");
                break;
            case  6:
                stringBuilderQuality.append("estimated");
                break;
            case  7:
                stringBuilderQuality.append("manual input mode");
                break;
            case  8:
                stringBuilderQuality.append("simulation mode");
                break;
            default:
                stringBuilderQuality.append("none");
                break;
        }
        return stringBuilderQuality.toString();
    }

}
