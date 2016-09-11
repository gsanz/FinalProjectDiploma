package audioguia.valencia;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;

import static android.media.MediaPlayer.create;


/**
 * Created by gorsanmo on 12/11/15.
 */
public class show_image extends FragmentActivity {

    private MediaPlayer audioguia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_foto);
        Intent myIntent = getIntent(); // gets the previously created intent
        String image = myIntent.getStringExtra("image"); // will return "FirstKeyValue"
        String lugar = myIntent.getStringExtra("lugar");
        String audio = myIntent.getStringExtra("audio");
        Button controlButton = (Button) findViewById(R.id.controlButton);
        ImageView img_show_image = (ImageView)findViewById(R.id.imagen_monumento);
        TextView textview = (TextView)findViewById(R.id.nombre_monumento);
        textview.setText(lugar);
        int id = getResources().getIdentifier("nautico.location:drawable/"+image, null, null);
        int id_audio = getResources().getIdentifier("nautico.location:raw/" + audio, null, null);
        audioguia = MediaPlayer.create(this,id_audio);
        img_show_image.setImageResource(id);
    }

    public void salir(View v)
    {
        this.finish();
    }

    public void parar(View v)
    {
        Button controlButton = (Button) findViewById(R.id.controlButton);
        if (audioguia.isPlaying()){
            controlButton.setText("Iniciar");
            audioguia.pause();}
        else{audioguia.start();
            controlButton.setText("Parar");
        }
    }

    @Override protected void onResume()
    {
        super.onResume();
        audioguia.start();
    }


    @Override protected void onPause()
    {
        super.onPause();
        audioguia.stop();
    }

    @Override protected void onSaveInstanceState(Bundle estadoGuardado)
    {
        super.onSaveInstanceState(estadoGuardado);
        if (audioguia !=null)
        {
            int pos = audioguia.getCurrentPosition();
            estadoGuardado.putInt("posicion",pos);
        }
    }

    @Override protected void onRestoreInstanceState(Bundle estadoguardado)
    {
        super.onRestoreInstanceState(estadoguardado);
        if (estadoguardado != null && audioguia != null)
        {
            int pos = estadoguardado.getInt("posicion");
            audioguia.seekTo(pos);
        }
    }
}
