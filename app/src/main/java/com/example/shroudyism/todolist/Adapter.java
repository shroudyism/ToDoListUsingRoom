package com.example.shroudyism.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.TaskHolder> {

    Context context;
    ArrayList<Task> arrayList;


    public Adapter(Context context, ArrayList<Task> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        view.findViewById(R.id.linear);

        RecyclerView rv=view.findViewById(R.id.rv);

        new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                arrayList.remove(i);

            }
        };


        return new TaskHolder(view);
    }




    public void onBindViewHolder( TaskHolder taskHolder, int i) {

        Task task=arrayList.get(i);

        taskHolder.setTitle(task.getTitle().toString());
        taskHolder.setDesc(task.getDesc().toString());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView desc;

        public TextView getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public TextView getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc.setText(desc);
        }

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

             title=(TextView) itemView.findViewById(R.id.trp);
             desc=(TextView) itemView.findViewById(R.id.t);


        }
    }

}
