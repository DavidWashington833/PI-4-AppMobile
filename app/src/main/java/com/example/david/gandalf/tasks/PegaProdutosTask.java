package com.example.david.gandalf.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.example.david.gandalf.ProdutoFragment;
import com.example.david.gandalf.R;
import com.example.david.gandalf.WebClient;
import com.example.david.gandalf.adapter.ProdutoAdapter;
import com.example.david.gandalf.models.Categoria;
import com.example.david.gandalf.models.Produto;
import com.google.gson.Gson;

import java.util.Arrays;

/**
 * Created by fernando.hyamamoto on 16/11/2017.
 */

public class PegaProdutosTask extends AsyncTask<Void, Void, String> {
    private ProdutoFragment context;

    public PegaProdutosTask(ProdutoFragment context) {
        this.context = context;
}


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... params) {
        WebClient client = new WebClient();
        String resposta = client.get("http://gandalf.azurewebsites.net/gandalf/rest/produto/");
        return resposta;
    }

    @Override
    protected void onPostExecute(final String resposta) {
        if (!resposta.equals("null")) {
            Produto[] produtos = new Gson().fromJson(resposta, Produto[].class);
            final ProdutoAdapter adapter = new ProdutoAdapter(context.getContext(), Arrays.asList(produtos));
            final ListView listView = (ListView) context.getActivity().findViewById(R.id.list_produto);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    EditText reclicado = (EditText) context.getActivity().findViewById(R.id.hiddenIdProd);
                    reclicado.setText("0");

                    Produto p = (Produto) adapter.getItem(i);

                    EditText id = (EditText) context.getActivity().findViewById(R.id.hiddenIdProd);
                    id.setText(p.getIdProduto().toString());
                }
            });

            listView.setAdapter(adapter);
        }
    }
}