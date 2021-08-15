package com.example.navigationdrawer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a21_hg095_java.Dashboard;
import com.example.a21_hg095_java.MainActivity;
import com.example.a21_hg095_java.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    // 초기 변수 설정
    Activity activity;
    ArrayList<String> arrayList;

    // 생성자 만들기
    public MainAdapter(Activity activity, ArrayList<String> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 초기 변수 설정
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drawer_main, parent, false);
        //Return holder view
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Set text on text view
        holder.textView.setText(arrayList.get(position));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get clicked item position
                int position = holder.getAdapterPosition();
                //checked condition
                switch (position) {
                    case 0:
                        //포지션이 0이면  이용안내로 화면전환 사용하기
                        activity.startActivity(new Intent(activity, Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                       /* //initialize alert dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        //set title
                        builder.setTitle("Logout");
                        //set message
                        builder.setMessage("Are you sure you want to logout?");
                        //Positive yes button
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Finish all activity
                                activity.finishActivity(0);
                                //exit app
                                System.exit(0);
                            }
                        });
                        //negative cancel button
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss dialog
                                dialog.dismiss();
                            }
                        });
                        //show dialog
                        builder.show();*/
                        break;
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        // 리스트배열 크기 return
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //초기 변수 설정
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //변수 할당값
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}