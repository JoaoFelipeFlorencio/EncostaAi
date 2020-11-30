package encostai.encostai.com.br.encostaai.activity.termsPolicies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import encostai.encostai.com.br.encostaai.R;

public class TermsPoliciesActivity extends AppCompatActivity implements ITermsPoliciesView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
