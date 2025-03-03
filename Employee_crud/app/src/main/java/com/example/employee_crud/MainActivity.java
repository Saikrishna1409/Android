package com.example.employee_crud;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private DatabaseHelper databaseHelper;
    private List<Employee> employeeList;
    private EditText etName, etPhone, etAddress;
    private TextView etDob;
    private Spinner spinnerDepartment;
    private Button btnAdd;
    private Calendar calendar;
    private int editingEmployeeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        etName = findViewById(R.id.etName);
        etDob = findViewById(R.id.etDob);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnAdd = findViewById(R.id.btnAdd);

        List<Pair<String,String>> departments = new ArrayList<>();
        departments.add(new Pair("Development","#FF5733"));
        departments.add(new Pair("Testing","#2B7CBC"));
        departments.add(new Pair("BA","#BC9D41"));
        departments.add(new Pair("Devops","#FF000000"));
        departments.add(new Pair("HR","#6200EE"));

        loadDepartmentSpinner(departments);
        calendar = Calendar.getInstance();
        etDob.setOnClickListener(view -> showDatePicker());

        databaseHelper = new DatabaseHelper(this);
        employeeList = databaseHelper.getAllEmployees();

        adapter = new EmployeeAdapter(this, employeeList, departments, new EmployeeAdapter.OnEmployeeActionListener() {
            @Override
            public void onEdit(Employee employee) {
                etName.setText(employee.getName());
                etDob.setText(employee.getDob());
                etPhone.setText(employee.getPhone());
                etAddress.setText(employee.getAddress());
                editingEmployeeId = employee.getId();
                btnAdd.setText("Update");
            }

            @Override
            public void onDelete(int id) {
                databaseHelper.deleteEmployee(id);
                refreshList();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(view -> {
            String name = etName.getText().toString();
            String dob = etDob.getText().toString();
            String department = departments.get(spinnerDepartment.getSelectedItemPosition()).first;
            String phone = etPhone.getText().toString();
            String address = etAddress.getText().toString();

            if (validateInput(name, dob, department, phone, address) && editingEmployeeId==-1) {
                databaseHelper.addEmployee(new Employee(0, name, dob, department, phone, address));
                clearFields();
            } else {
                databaseHelper.updateEmployee(new Employee(editingEmployeeId, name, dob, department, phone, address));
                btnAdd.setText("Add Employee");
                editingEmployeeId = -1;
            }
            refreshList();
        });
    }

    private boolean validateInput(String name, String dob, String department, String phone, String address) {
        // Validate Name (must not be empty)
        if (name.isEmpty()) {
            etName.setError("Enter employee name");
            etName.requestFocus();
            return false;
        }

        // Validate DOB (must not be empty)
        if (dob.isEmpty()) {
            etDob.setError("Select date of birth");
            etDob.requestFocus();
            return false;
        }

        // Validate Department (must be selected)
        if (department.equals("Select Department")) {  // Ensure your first item in the spinner is "Select Department"
            Toast.makeText(this, "Please select a department", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Phone Number (must be 10 digits)
        if (phone.isEmpty() || phone.length() != 10 || !phone.matches("\\d{10}")) {
            etPhone.setError("Enter a valid 10-digit phone number");
            etPhone.requestFocus();
            return false;
        }

        // Validate Address (must not be empty)
        if (address.isEmpty()) {
            etAddress.setError("Enter address");
            etAddress.requestFocus();
            return false;
        }

        return true;  // All validations passed
    }

    private void clearFields() {
        etName.setText("");
        etDob.setText("");
        etPhone.setText("");
        etAddress.setText("");
        spinnerDepartment.setSelection(0);
    }
    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the date
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                    etDob.setText(sdf.format(calendar.getTime()));
                },
                year, month, day
        );
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }
    private void loadDepartmentSpinner(List<Pair<String,String>> departments) {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, departments);
        spinnerDepartment.setAdapter(adapter);
    }
    private void refreshList() {
        employeeList.clear();
        employeeList.addAll(databaseHelper.getAllEmployees());
        adapter.notifyDataSetChanged();
    }
}
