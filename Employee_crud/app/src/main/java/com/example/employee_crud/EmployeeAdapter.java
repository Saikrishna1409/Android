package com.example.employee_crud;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> employeeList;
    private Context context;
    private OnEmployeeActionListener listener;
    List<Pair<String,String>> departments;

    public interface OnEmployeeActionListener {
        void onEdit(Employee employee);
        void onDelete(int id);
    }

    public EmployeeAdapter(Context context, List<Employee> employees, List<Pair<String,String>> departments, OnEmployeeActionListener listener) {
        this.context = context;
        this.employeeList = employees;
        this.listener = listener;
        this.departments=departments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.name.setText("Name : "+employee.getName());
        holder.dob.setText("D.O.B : " + employee.getDob());
        holder.department.setText("Dept : "+employee.getDepartment());
        holder.department.setTextColor(Color.parseColor(departments.get(position).second));
        holder.phone.setText("Phone : "+employee.getPhone());
        holder.address.setText("Address : "+employee.getAddress());
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(employee));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(employee.getId()));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, dob, department, phone, address;
        ImageView btnEdit, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            dob = itemView.findViewById(R.id.tvDob);
            department = itemView.findViewById(R.id.tvDepartment);
            phone = itemView.findViewById(R.id.tvPhone);
            address = itemView.findViewById(R.id.tvAddress);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
    public void refreshList(List<Employee> employeeList) {
        employeeList.clear();
        employeeList.addAll(employeeList);
        notifyDataSetChanged();
    }
}

