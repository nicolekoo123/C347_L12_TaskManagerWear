package sg.edu.rp.c346.id19047433.l12_taskmanagerwear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Tasks> {
    Context context;
    ArrayList<Tasks> tasks;
    int resource;
    TextView tvCombine,tvDesc;


    public TaskAdapter(Context context, int resource, ArrayList<Tasks> tasks) {
        super(context, resource, tasks);
        this.context = context;
        this.tasks = tasks;
        this.resource = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);

        tvCombine = (TextView) rowView.findViewById(R.id.tvCombine);
        tvDesc = (TextView) rowView.findViewById(R.id.tvDesc);

        Tasks task = tasks.get(position);

        tvCombine.setText((position+1) +" "+task.getName());
        tvDesc.setText(task.getDescription());

        return rowView;
    }
}