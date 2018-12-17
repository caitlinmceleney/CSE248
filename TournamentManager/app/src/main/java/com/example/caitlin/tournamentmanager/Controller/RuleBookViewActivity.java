package com.example.caitlin.tournamentmanager.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.caitlin.tournamentmanager.R;
import com.github.barteksc.pdfviewer.PDFView;

public class RuleBookViewActivity extends AppCompatActivity {

    PDFView rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_book);

        rules = (PDFView)findViewById(R.id.rulePDF);
        rules.fromAsset("USAU Rules.pdf").load();
    }
}
