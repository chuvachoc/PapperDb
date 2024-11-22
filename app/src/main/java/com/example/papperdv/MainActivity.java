package com.example.papperdv;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private EditText tittleText, AuthorText;
    private Button addButton, updateButton, deleteButton;
    private ListView listView;

    private ArrayAdapter<String> adapter;
    private String selectedBookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        tittleText = findViewById(R.id.titleText);
        AuthorText = findViewById(R.id.AuthorText);
        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getBooksTitles());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBookTitle = adapter.getItem(position);
                Book book = Paper.book().read(selectedBookTitle, null);

                if (book != null) {
                    tittleText.setText(book.getName_book());
                    AuthorText.setText(book.getBook_author());
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = tittleText.getText().toString();
                String content = AuthorText.getText().toString();

                if (!title.isEmpty() && !content.isEmpty()) {
                    Book book = new Book(title, content);
                    Paper.book().write(title, book);
                    updateBookList();
                    clearInputs();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedBookTitle != null) {
                    String title = tittleText.getText().toString();
                    String content = AuthorText.getText().toString();

                    if (!title.isEmpty() && !content.isEmpty()) {
                        Book updatedBook = new Book(title, content);
                        Paper.book().write(selectedBookTitle, updatedBook);
                        updateBookList();
                        clearInputs();
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedBookTitle != null) {
                    Paper.book().delete(selectedBookTitle);
                    updateBookList();
                    clearInputs();
                }
            }
        });
    }

    private void updateBookList() {
        adapter.clear();
        adapter.addAll(getBooksTitles());
        adapter.notifyDataSetChanged();
    }

    private List<String> getBooksTitles() {
        List<String> titles = new ArrayList<>();
        List<String> keys = Paper.book().getAllKeys();
        for (String key : keys) {
            titles.add(key);
        }
        return titles;
    }

    private void clearInputs() {
        tittleText.setText("");
        AuthorText.setText("");
    }
}