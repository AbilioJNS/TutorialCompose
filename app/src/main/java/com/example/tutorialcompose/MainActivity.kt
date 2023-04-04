package com.example.tutorialcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tutorialcompose.ui.theme.TutorialComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorialComposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Conversacion(SampleData.conversacion)
                }
            }
        }
    }
}

data class Message(val autor: String, val body:String)

@Composable
fun MessageCard(sms: Message){
    Row {
        Image(
            painter = painterResource(R.drawable.necesidad_),
            contentScale = ContentScale.Crop,
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // cambiamos el tamaño de la imagen a 40 dp
                .size(40.dp)
                // cambiamos la imagen a la forma de circulo
                .clip(CircleShape)
                //Añadimos un borde a la imagen
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        //Añadimos un espacio entre la imagen y el texto
        Spacer(modifier = Modifier.width(8.dp))

        // Realizamos un seguimiento si el mensaje se expande o no en este
        // variable
        var isExpanded by remember {mutableStateOf(false)}
        // el color de la superficie se actualizará gradualmente de un color al otro
        val colorSurface by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary
            else MaterialTheme.colors.surface
        )
        Column (modifier = Modifier.clickable { isExpanded = !isExpanded }) {

            Text(
                text = sms.autor,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface (
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // el color del color de la superficie cambiará gradualmente de primario a superficial
                color = colorSurface,
                // animateContentSize cambiará el tamaño de la superficie gradualmente
                modifier = Modifier.animateContentSize().padding(1.dp)

                ) {
                Text(
                    text = sms.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // Si el mensaje esta expandido, mostramos todo su contenido
                    // de lo contrario, solo mostramos la primera línea
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun Conversacion(mensajes: List<Message>){
    LazyColumn {
        items(mensajes) { mensaje ->
            MessageCard(mensaje)
        }
    }
}

@Preview
@Composable
fun PreviewConversacion() {
    TutorialComposeTheme() {
        Conversacion(SampleData.conversacion)
    }
}