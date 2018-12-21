package com.example.shroudyism.todolist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private AppDatabase mDb;
    public ArrayList<Task> arrayList=new ArrayList<>();
    Context context;
    Adapter  adapter=new Adapter(context,arrayList);
    FloatingActionButton ftbtn;
    RecyclerView rv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ftbtn = findViewById(R.id.addbtn);
        rv =findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        mDb=AppDatabase.getsInstance(MainActivity.this);


        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ftbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                CustomDialogClass cdd = new CustomDialogClass(MainActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });




        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                CustomDetailClass cdd = new CustomDetailClass(MainActivity.this,target.toString());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

                return false;
            }



            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                adapter.arrayList.remove(position);
                adapter.notifyDataSetChanged();

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv);

        adapter.notifyDataSetChanged();


    }



    @Override
    protected void onResume(){
        super.onResume();

        ArrayList<Task> temp= (ArrayList<Task>) mDb.taskDao().loadAllTasks();

        arrayList=temp;
        adapter.notifyDataSetChanged();

    }

    public class CustomDetailClass extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        public String target;

        EditText etTitle;
        EditText etDesc;

        public CustomDetailClass(Activity a,String target) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_task_details);

            etTitle=findViewById(R.id.titledetail);
            etDesc=findViewById(R.id.descdetail);

            etTitle.setText(arrayList.get(Integer.parseInt(target)).getTitle().toString());
            etDesc.setText(arrayList.get(Integer.parseInt(target)).getDesc().toString());

        }


        @Override
        public void onClick(View v) {

        }
    }


    public class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button yes, no;

        EditText etTitle;
        EditText etDesc;

        public CustomDialogClass(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.addnewdialog);
            yes = (Button) findViewById(R.id.save);
            no = (Button) findViewById(R.id.cancel);
            etTitle=findViewById(R.id.titledialog);
            etDesc=findViewById(R.id.descdialog);


            yes.setOnClickListener(this);
            no.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save:
                {

                    Task task=new Task(etTitle.getText().toString(),etDesc.getText().toString());

                    mDb.taskDao().insertTask(task);

                    adapter.notifyDataSetChanged();
                   // arrayList.add(task);
                    //adapter.notifyDataSetChanged();

                    dismiss();
                }
                break;
                case R.id.cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }
}
