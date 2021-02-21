package stu.mobtech.listview.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var groupsListView: ListView
    private lateinit var groups: Array<String>
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        groupsListView = findViewById(R.id.groups_list_view)
        groups = resources.getStringArray(R.array.student_groups)

        adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, groups)
        groupsListView.adapter = adapter

        groupsListView.setOnItemClickListener { adapterView, view, i, l ->
            val selectedItem = groups[i]
            val intent = GroupDetailsActivity.newIntent(this@MainActivity, selectedItem)
            startActivity(intent)

        }

    }
}