package com.gandalf.tasks;

import android.os.AsyncTask;
import android.widget.ListView;

import com.gandalf.CarrinhoFragment;
import com.gandalf.R;
import com.gandalf.adapter.CarrinhoAdapter;
import com.gandalf.helpers.CarrinhoSingletonHelper;
import com.gandalf.models.Produto;
import com.google.gson.Gson;

import java.util.Arrays;

/**
 * Created by gabriel.elmontibelle on 21/11/2017.
 */

public class AlimentaCarrinhoTask extends AsyncTask<Void, Void, String> {
    private CarrinhoFragment context;

    public AlimentaCarrinhoTask(CarrinhoFragment context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String resposta ="";
        return resposta;

    }

    @Override
    protected void onPostExecute(String resposta) {

          String result = new Gson().toJson(CarrinhoSingletonHelper.getInstance().getProduto());
        if (!result.equals("null")) {

            Produto[] produtos = new Gson().fromJson(result, Produto[].class);
            final CarrinhoAdapter adapter = new CarrinhoAdapter(context.getContext(), Arrays.asList(produtos));
            final ListView listView = (ListView) context.getActivity().findViewById(R.id.list_produto_carrinho);

            listView.setAdapter(adapter);

        }
    }
}

