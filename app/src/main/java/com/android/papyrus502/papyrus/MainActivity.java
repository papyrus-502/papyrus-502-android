package com.android.papyrus502.papyrus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> wordList;
    private ListView listView;

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final String A_FILE_NAME = "a_file.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        this.wordList = new ArrayList<>();

        writeXmlText();
        readXml();
        //updateList();

        Button newWordButton = (Button)findViewById(R.id.add_new_word);
        //newWordEditText.setEnabled(false);
        //newWordButton.setTypeface(Typeface.DEFAULT_BOLD);
        newWordButton.setText("    Add a new word...");
        newWordButton.setAllCaps(false);
        newWordButton.setGravity(Gravity.START);
        newWordButton.setTextColor(Color.GRAY);
        newWordButton.setBackgroundColor(Color.WHITE);
        newWordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
                Intent wordIntent = new Intent( MainActivity.this, WordActivity.class );
                startActivityForResult(wordIntent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*
        * Add list
        * */
        listView = (ListView) findViewById(R.id.list_view);

        updateList();
    }

    public void writeXmlText(){
        //
        //  write a xml
        //
        FileOutputStream fos;
        try {
            fos = openFileOutput(A_FILE_NAME, Context.MODE_PRIVATE);
            //default mode is PRIVATE, can be APPEND etc.
            fos.write("<words><word>word_here</word><word>word2_here</word><word>word3_here</word></words>".getBytes());
            fos.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    public void readXml(){
        try {
            InputStream is = openFileInput(A_FILE_NAME);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nodeList = element.getElementsByTagName("word");
            for(int j = 0; j < nodeList.getLength(); j++){

                NodeList nodeListB = (NodeList)nodeList.item(j).getChildNodes();
                for (int i=0; i<nodeListB.getLength(); i++) {
                    Node node = (Node) nodeListB.item(i);
                    wordList.add(node.getNodeValue());
                }//end of for loop
            }

        } catch (Exception e) {e.printStackTrace();}
    }

    public void updateList(){
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, wordList.toArray(new String[]{}));

        listView.setAdapter(listViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                String returnString = data.getStringExtra("word");

                wordList.add(returnString);
                updateList();
            }
        }
    }
}
