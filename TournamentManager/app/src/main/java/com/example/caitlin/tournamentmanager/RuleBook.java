package com.example.caitlin.tournamentmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class RuleBook extends AppCompatActivity {

    PDFView rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_book);

        rules = (PDFView)findViewById(R.id.rulePDF);
        rules.fromAsset("USAU Rules.pdf").load();
    }
}
