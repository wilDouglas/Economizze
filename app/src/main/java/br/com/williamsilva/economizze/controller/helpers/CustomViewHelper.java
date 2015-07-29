package br.com.williamsilva.economizze.controller.helpers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import br.com.williamsilva.economizze.R;

/**
 * Created by william on 13/02/15.
 */
public class CustomViewHelper {

    private Context context;

    public CustomViewHelper(Context context) {
        this.context = context;
    }

    public void customizarTextViewValorNegativo(Double valor, TextView textView) {
        if(valor < 0) {
            textView.setTextColor(context.getResources().getColor(R.color.vermelho));
        }
        else{
            textView.setTextColor(context.getResources().getColor(R.color.verde));
        }
    }

    public void customizarBackgroundCardViewValorNegativo(Double valor, CardView card){
        if(valor < 0){

            card.setCardBackgroundColor(context.getResources().getColor(R.color.vermelho));
        }
        else{
            card.setCardBackgroundColor(context.getResources().getColor(R.color.verde));
        }
    }


}
