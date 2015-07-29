package br.com.williamsilva.economizze.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.williamsilva.economizze.R;
import br.com.williamsilva.economizze.model.ItemListViewMenu;

/**
 * Created by William on 20/11/2014.
 */
public class AdapterListViewMenu extends BaseAdapter {

    public static Integer itemPosition = Integer.MIN_VALUE;

    private LayoutInflater mInflater;
    private List<ItemListViewMenu> itens;
    private Context context;

    public AdapterListViewMenu(Context context, List<ItemListViewMenu> itens) {
        this.mInflater = LayoutInflater.from(context);
        this.itens = itens;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ItemSuporte itemHolder;

        if(view == null)
        {
            view = mInflater.inflate(R.layout.item_list_and_image,null);
            itemHolder = new ItemSuporte();
            itemHolder.txtTitle = ((TextView) view.findViewById(R.id.textmenu));
            itemHolder.imgIcon = ((ImageView) view.findViewById(R.id.imgviewmenu));

            view.setTag(itemHolder);
        }
        else{
            itemHolder = (ItemSuporte) view.getTag();
        }

        this.criarSeletor(position,view);


        ItemListViewMenu item = itens.get(position);
        itemHolder.txtTitle.setText(item.getTexto());
        itemHolder.imgIcon.setImageResource(item.getImagemId());

        return view;
    }

    private void criarSeletor(Integer position,View view) {
        LinearLayout ln = (LinearLayout) view.findViewById(R.id.listln);
        TextView tx = (TextView) view.findViewById(R.id.textmenu);

        if(itemPosition.equals(position)) { // criando seletor e setando a cor do texto mediante a selecao do usuario
            ln.setBackgroundColor(context.getResources().getColor(R.color.preto_claro));
            switch (itemPosition){
                case 1:
                    tx.setTextColor(view.getResources().getColor(R.color.verde));
                    break;

                case 2:
                    tx.setTextColor(view.getResources().getColor(R.color.vermelho_claro));
                    break;
                case 3:
                    tx.setTextColor(view.getResources().getColor(R.color.azul_piscina));
                    break;

                default:
                    tx.setTextColor(view.getResources().getColor(R.color.branco));
                    break;
            }
        }
        else
        {
            ln.setBackgroundColor(context.getResources().getColor(R.color.preto));
            tx.setTextColor(view.getResources().getColor(R.color.branco));
        }
    }

    /* Classe de suporte para os itens do Layout */
    private class ItemSuporte{
        ImageView imgIcon;
        TextView txtTitle;
    }
}
