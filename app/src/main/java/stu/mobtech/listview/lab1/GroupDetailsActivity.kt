package stu.mobtech.listview.lab1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.util.keyIterator

private const val EXTRA_GROUP_NAME = "GROUP_NAME"

class GroupDetailsActivity : AppCompatActivity() {

    private val selectedStudents = ArrayList<String>()
    private lateinit var groupName: String
    private lateinit var students: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var studentsListView: ListView
    private lateinit var selectedStudentsTextView: TextView
    private lateinit var studentNameTextEditText: EditText
    private lateinit var addStudentButton: Button
    private lateinit var removeStudentButton: Button

    companion object {

        fun newIntent(packageContext: Context, groupName: String): Intent {
            return Intent(packageContext, GroupDetailsActivity::class.java).apply {
                putExtra(EXTRA_GROUP_NAME, groupName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)

        groupName = intent.getStringExtra(EXTRA_GROUP_NAME)

        selectedStudentsTextView = findViewById(R.id.selected_students_text_view)
        studentNameTextEditText = findViewById(R.id.student_name_edit_text)

        addStudentButton = findViewById(R.id.add_student_button)
        addStudentButton.setOnClickListener {
            val name = studentNameTextEditText.text.toString()
            adapter.add(name)
            studentNameTextEditText.text.clear()
            adapter.notifyDataSetChanged()
        }

        removeStudentButton = findViewById(R.id.remove_student_button)
        removeStudentButton.setOnClickListener {
            for (student in selectedStudents) {
                adapter.remove(student)
            }
            studentsListView.clearChoices()
            selectedStudents.clear()
            adapter.notifyDataSetChanged()
        }

        studentsListView = findViewById(R.id.students_list_view)
        studentsListView.setOnItemClickListener { adapterView, view, i, l ->
            val checkedStudents = studentsListView.checkedItemPositions
            val student = adapter.getItem(i)!!
            if (studentsListView.isItemChecked(i)) {
                selectedStudents.add(student)
            } else {
                selectedStudents.remove(student)
            }
            var selectedText = ""
            for (j in checkedStudents.keyIterator()) {
                if (checkedStudents.get(j)) {

                    selectedText += students[j] + " "
                }
            }
            selectedStudentsTextView.text = selectedText
        }

        setListSource()
    }

    private fun setListSource() {
        students = when (groupName) {
            "БПИ-411" -> resources.getStringArray(R.array.bpi).toCollection(ArrayList())
            "БИСТ-411" -> resources.getStringArray(R.array.bist).toCollection(ArrayList())
            else -> resources.getStringArray(R.array.not_exist).toCollection(ArrayList())
        }
        adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, students)
        studentsListView.adapter = adapter
    }
}