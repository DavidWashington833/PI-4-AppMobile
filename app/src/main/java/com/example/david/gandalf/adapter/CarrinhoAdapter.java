package com.example.david.gandalf.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.gandalf.MainActivity;
import com.example.david.gandalf.ProdutoUnicoFragment;
import com.example.david.gandalf.CarrinhoFragment;
import com.example.david.gandalf.R;
import com.example.david.gandalf.helpers.CarrinhoSingletonHelper;
import com.example.david.gandalf.models.Produto;

import java.util.List;

/**
 * Created by gabriel.elmontibelle on 23/11/2017.
 */

public class CarrinhoAdapter  extends BaseAdapter {
    private final List<Produto> produtos;
    private final Context context;

    public CarrinhoAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(produtos.get(position).getIdProduto());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Produto produto = produtos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.cardview_carrinho, parent, false);
        }

        final ViewGroup parentView = parent;

        TextView campoNome = (TextView) view.findViewById(R.id.txtNomeProdCart);
        campoNome.setText(produto.getNomeProduto());

        TextView campoPreco = (TextView) view.findViewById(R.id.txtPrecoCart);
        campoPreco.setText(produto.getPrecProduto());

        ImageView campoImg = (ImageView) view.findViewById(R.id.imagemProdCart);
        String imagem = produto.getImagem();
        if (imagem != null) {
            byte[] imageAsBytes = Base64.decode(imagem.getBytes(), Base64.DEFAULT);
            campoImg.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }

        final EditText campoId = (EditText) view.findViewById(R.id.hiddenIdProdCarrinho);
        campoId.setText(produto.getIdProduto());

        Button botaoDetalhes = (Button) view.findViewById(R.id.btnVisualizarCarrinho);

        botaoDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaFragment(new ProdutoUnicoFragment(campoId.getText().toString()));
//                Toast.makeText(v.getContext(), campoId.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        Button botaoRemover = (Button) view.findViewById(R.id.btnRemoverProduto);

        botaoRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarrinhoSingletonHelper.getInstance().removeProduto(produto);
                for (int i = 0; i < produtos.size(); i++){
                    if(produto.getIdProduto().equals(produtos.get(i).getIdProduto())){
                        produtos.remove(i);
                        break;
                    }
                }
                notifyDataSetChanged();
                Toast.makeText(context, "Produto removido com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void chamaFragment(Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = ((MainActivity)context).getSupportFragmentManager();
        manager.popBackStackImmediate(backStateName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_principal, fragment);
        transaction.addToBackStack(backStateName);
        transaction.commit();
    }
}