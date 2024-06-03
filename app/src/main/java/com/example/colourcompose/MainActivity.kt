package com.example.colourcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.example.colourcompose.ui.theme.ColourComposeTheme
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColourComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationStack()
                }
            }
        }
    }
}
fun createGradientBrush(
    colors: List<Color>,
    isVertical: Boolean = true
): Brush {
    val endOffset = if (isVertical) {
        Offset(0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }
    return Brush.linearGradient(
        colors = colors,
        start = Offset(0f, 0f),
        end = endOffset,
        tileMode = TileMode.Clamp
    )
}
@Composable
fun RulesDialog(onDismiss: () -> Unit) {
    val aladin= FontFamily(
        Font(R.font.aladin)
    )
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .width(300.dp)
            ) {
                Text(text = "Game Rules",fontSize = 30.sp,fontWeight = FontWeight.Bold,fontFamily= aladin )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "1)Players can choose any tile on the grid on this turn only. " +
                        "Clicking a tile assigns your colour to it and awards you 3 points on that tile.\n" +
                        "2) After the first turn, players can only click on tiles that already have their own colour. Clicking a " +
                        "tile with your colour adds 1 point to that tile.The background colour indicates the next player.\n" +
                        "3)When a tile with your colour reaches 4 points, it triggers an expansion:The colour completely disappears from the original tile.\n" +
                        "Your colour spreads to the four surrounding squares in a plus shape (up, down, left, right).\n" +
                        "Each of the four surrounding squares gains 1 point with your colour.\n" +
                        "4)If any of the four has your opponent’s colour, you conquer the opponent's " +
                        "points on that tile while adding a point to it, completely erasing theirs.\n" +
                        "The expansion is retriggered if the neighbouring tile as well reaches 4 points this way.\n"+
                        "5)Players take turns clicking on tiles and the objective is to eliminate your opponent's colour entirely from the screen.")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Close",fontWeight = FontWeight.Bold,fontSize = 25.sp,fontFamily= aladin )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier : Modifier=Modifier,navController: NavController) {
    val gradientColors = listOf(
        Color(4294883696),
        Color(4294944880),
        Color(4294928237)
    )
    val textgradient = listOf(
        Color(4289357924),
        Color(4290080618),
        Color(4290211948)
    )
    val leckerli= FontFamily(
        Font(R.font.leckerli_one)
    )
    val text = remember {
        mutableStateOf("")
    }
    val indigo = Color(0xFF2FB6F0)
    val darkblue = Color(0xFF3B4276)

    Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = createGradientBrush(
                        colors = gradientColors,
                        isVertical = true
                    )
                )
        )
    Column(verticalArrangement = Arrangement.Top)
        {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                "COLOR",
                textAlign = TextAlign.Center,
                fontSize = 70.sp,
                fontFamily= leckerli ,
                modifier = Modifier.width(500.dp).height(80.dp)
            )
            Text(
                "CONQUEST",
                textAlign = TextAlign.Center,
                fontSize = 70.sp, fontFamily= leckerli ,
                modifier = Modifier.width(800.dp).height(800.dp),
                style = TextStyle(brush = Brush.linearGradient(colors = textgradient))
            )
        }
    Image(
            painter = painterResource(id = R.drawable.rednblueicon),
            contentDescription = "Player Icons"
        )
    var showDialog by remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .absolutePadding(10.dp, 650.dp, 30.dp, 0.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {navController.navigate(route = Screen.Detail.route + "?text=${text.value}")},
            modifier = Modifier
                .height(80.dp)
                .width(199.dp) ,
            colors = ButtonDefaults.buttonColors(containerColor = indigo))
        {
            Text("PLAY", color = Color.White,fontSize = 30.sp,fontWeight=FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick =  { showDialog = true }, shape = CircleShape, modifier = Modifier.size(75.dp),
            colors = ButtonDefaults.buttonColors(containerColor = darkblue))
        {
            Text(
                text = "?", fontSize = 45.sp, fontWeight = FontWeight.Bold, color = Color.White
            )
        }
        if (showDialog) {
            RulesDialog(onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun DetailScreen(modifier: Modifier = Modifier,text: String?) {
    val gradientColors = listOf(
        Color(4294883696),
        Color(4294944880),
        Color(4294928237)
    )
    val indigo = Color(0xFF2FB6F0)
    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                brush = createGradientBrush(
                    colors = gradientColors,
                    isVertical = true
                )
            )
    )
    Image(
        painter = painterResource(id = R.drawable.rednblueicon),
        contentDescription = "A call icon for calling",
        modifier = Modifier.offset(x = 0.dp, y = 100.dp).fillMaxSize(),
    )

    Column(verticalArrangement = Arrangement.Top,horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(" ",
            modifier = Modifier
                .width(500.dp)
                .height(45.dp))
    Button(
        onClick = {  },modifier = Modifier
            .height(60.dp)
            .width(369.dp),
        shape = RoundedCornerShape(70.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(4294759604), contentColor = Color.Black
        )
    ) {
        Text(text = "PLAYER INFORMATION",fontSize = 25.sp)
    }}
    Column (verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,modifier = Modifier.padding( start= 36.dp))
    {
        Spacer(modifier = Modifier.height(190.dp))
        PlayerBadge()
        Spacer(modifier = Modifier.height(20.dp))
        PlayerBadge2()
        Spacer(modifier = Modifier.height(20.dp))
    }
    Box ()
    {Column (verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start, modifier = Modifier.padding( start= 236.dp))
    {
        Spacer(modifier = Modifier.height(190.dp))
        BlueBox()
        Spacer(modifier = Modifier.height(20.dp))
        BlueBox()
    }}
    Box ()
    {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start = 236.dp,top=275.dp)
        )
        {
            Text(
                text = "  _ _ _ _ _ _ _ _ _ _ _", color = Color(0xFFFF6B6B),
                fontSize = 21.sp, fontWeight = FontWeight.Bold
            )
        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start = 236.dp,top=415.dp)
        )
        {
            Text(
                text = "  _ _ _ _ _ _ _ _ _ _ _", color = Color(0xFF2FB6F0),
                fontSize = 21.sp, fontWeight = FontWeight.Bold
            )
        }
    }
    Box{
        Column (verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start, modifier = Modifier.padding( start= 290.dp))
        {   Spacer(modifier = Modifier.height(190.dp))
            Image(
            painter = painterResource(id =  R.drawable.redicon),
            contentDescription = "Red Player Icon",
                modifier = Modifier.size(77.dp))
            Spacer(modifier = Modifier.height(54.dp))
            Image(
                painter = painterResource(id =  R.drawable.blueicon),
                contentDescription = "Blue Player Icon",
                modifier = Modifier.size(77.dp)
            )}
        Column (verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start, modifier = Modifier.padding( start= 219.dp,bottom=354.dp))
        {
            TransparentTextField(1)
        }
        Column (verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start, modifier = Modifier.padding( start= 219.dp,bottom=75.dp))
        {
            TransparentTextField(2)
        }
    }
    Row(
        Modifier.fillMaxWidth().absolutePadding(10.dp, 650.dp, 30.dp, 0.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(onClick = { },modifier = Modifier.height(80.dp).width(199.dp) ,
            colors = ButtonDefaults.buttonColors(containerColor = indigo)) {
            Text("START", color = Color.White,fontSize = 30.sp,fontWeight=FontWeight.Bold)
        }
    }
}
@Composable
fun PlayerBadge() {
    Surface(
        modifier = Modifier
            .width(185.dp)
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(4282139764)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "1", fontSize = 45.sp, fontWeight = FontWeight.Bold,
                color = Color(0xFFFF6B6B)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(Color(0xFFFF6B6B), RoundedCornerShape(50.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PLAYER", color = Color.White, fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PlayerBadge2() {
    Surface(
        modifier = Modifier.width(185.dp).height(120.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(4282139764)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "2", fontSize = 45.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2FB6F0)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(Color(0xFF2FB6F0), RoundedCornerShape(50.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PLAYER", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
fun BlueBox(){
    Surface(
        modifier = Modifier.width(185.dp).height(120.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(4282139764) ){
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTextField(num:Int) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var playername= TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Enter Player-$num Name" )},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor =(Color(0xFF7680C0)),
                unfocusedTextColor =(Color(0xFF7680C0)),
                focusedLabelColor =(Color(0xFF7680C0)),
                focusedPlaceholderColor = (Color(0xFF7680C0)) ,
                unfocusedPlaceholderColor = (Color(0xFF7680C0))
            ), textStyle = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.background(Color.Transparent).padding(16.dp)
        )
    }
}

@Composable
fun Game(name: String, modifier: Modifier = Modifier) {
    val buttonStates = remember { mutableStateOf(Array(5) { Array<Int>(5){0}})}
    val currentPlayer = remember { mutableStateOf("Player 1") }
    val colors = Array(5) {
        Array(5) {
            Color.Red
        }
    }
    var k=0
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        for (i in 0 until 5) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (j in 0 until 5) {
                    Button(
                        onClick = {
                            if (buttonStates.value[i][j]==0 && k ==0 )
                            {
                                buttonStates.value[i][j]=3
                                k++
                                colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                currentPlayer.value =if (currentPlayer.value=="Player 1") "Player 2" else "Player 1"

                            }
                            else if (buttonStates.value[i][j]==0 && k ==1)
                            {
                                buttonStates.value[i][j]=3
                                k++
                                colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                currentPlayer.value =if (currentPlayer.value=="Player 1") "Player 2" else "Player 1"
                            }
                            else if (buttonStates.value[i][j]==3 )
                            {
                                if (i==0 && j==0 ) {
                                    buttonStates.value[i][j] = 0
                                    buttonStates.value[i + 1][j] += 1
                                    buttonStates.value[i][j + 1] += 1
                                    colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                    colors[i + 1][j]= colors[i][j]
                                    colors[i][j + 1]= colors[i][j]
                                    currentPlayer.value = if (currentPlayer.value == "Player 1") "Player 2" else "Player 1"
                                }
                                else if (i==0 && j==4 ) {
                                    buttonStates.value[i][j] = 0
                                    buttonStates.value[i][j - 1] += 1
                                    buttonStates.value[i + 1][j] += 1
                                    colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                    colors[i][j - 1]= colors[i][j]
                                    colors[i + 1][j]=colors[i][j]
                                    currentPlayer.value = if (currentPlayer.value == "Player 1") "Player 2" else "Player 1"
                                }
                                else if (i==4 && j==0 ) {
                                    buttonStates.value[i][j] = 0
                                    buttonStates.value[i][j +1] += 1
                                    buttonStates.value[i -1][j] += 1
                                    colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                    colors[i][j+1]=colors[i][j]
                                    colors[i-1][j]=colors[i][j]
                                    currentPlayer.value = if (currentPlayer.value == "Player 1") "Player 2" else "Player 1"
                                }
                                else if (i==4 && j==4 ) {
                                    buttonStates.value[i][j] = 0
                                    buttonStates.value[i-1][j ] += 1
                                    buttonStates.value[i][j-1] += 1
                                    colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                    colors[i-1][j]=colors[i][j]
                                    colors[i][j-1]=colors[i][j]
                                    currentPlayer.value = if (currentPlayer.value == "Player 1") "Player 2" else "Player 1"
                                }
                                else if (i==0 && (j==1 || j == 2 || j== 3))
                                {
                                    buttonStates.value[i][j] = 0
                                    buttonStates.value[i+1][j ] += 1
                                    buttonStates.value[i][j-1] += 1
                                    buttonStates.value[i][j+1] += 1
                                    colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                    colors[i+1][j]=colors[i][j]
                                    colors[i][j-1]=colors[i][j]
                                    colors[i][j+1]=colors[i][j]
                                    currentPlayer.value = if (currentPlayer.value == "Player 1") "Player 2" else "Player 1"
                                }
                                else if (i==4 && (j==1 || j == 2 || j== 3))
                                {
                                    buttonStates.value[i][j] = 0
                                    buttonStates.value[i-1][j ] += 1
                                    buttonStates.value[i][j-1] += 1
                                    buttonStates.value[i][j+1] += 1
                                    colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                    colors[i-1][j]=colors[i][j]
                                    colors[i][j-1]=colors[i][j]
                                    colors[i][j+1]=colors[i][j]
                                    currentPlayer.value = if (currentPlayer.value == "Player 1") "Player 2" else "Player 1"
                                }
                                else if (j==0 && (i==1 || i == 2 || i== 3))
                                {
                                    buttonStates.value[i][j] = 0
                                    buttonStates.value[i+1][j] += 1
                                    buttonStates.value[i-1][j] += 1
                                    buttonStates.value[i][j+1] += 1
                                    colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                    colors[i+1][j]=colors[i][j]
                                    colors[i-1][j]=colors[i][j]
                                    colors[i][j+1]=colors[i][j]
                                    currentPlayer.value = if (currentPlayer.value == "Player 1") "Player 2" else "Player 1"
                                }
                                else if (j==4 && (i==1 || i == 2 || i== 3))
                                {
                                    buttonStates.value[i][j] = 0
                                    buttonStates.value[i+1][j] += 1
                                    buttonStates.value[i-1][j] += 1
                                    buttonStates.value[i][j-1] += 1
                                    colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                    colors[i+1][j]=colors[i][j]
                                    colors[i-1][j]=colors[i][j]
                                    colors[i][j-1]=colors[i][j]
                                    currentPlayer.value = if (currentPlayer.value == "Player 1") "Player 2" else "Player 1"
                                }
                                else {
                                    buttonStates.value[i][j] = 0
                                    buttonStates.value[i+1][j] += 1
                                    buttonStates.value[i-1][j] += 1
                                    buttonStates.value[i][j-1] += 1
                                    buttonStates.value[i][j+1] += 1
                                    colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                    colors[i+1][j]=colors[i][j]
                                    colors[i-1][j]=colors[i][j]
                                    colors[i][j-1]=colors[i][j]
                                    colors[i][j+1]=colors[i][j]
                                    currentPlayer.value = if (currentPlayer.value == "Player 1") "Player 2" else "Player 1"
                                }
                            }
                            else if (buttonStates.value[i][j]==0 || buttonStates.value[i][j]==1 || buttonStates.value[i][j]==2)
                            {
                                buttonStates.value[i][j]+=1
                                colors[i][j]= if (currentPlayer.value=="Player 1") Color(0xFFFF6B6B) else Color(0xFF2FB6F0)
                                currentPlayer.value =if (currentPlayer.value=="Player 1") "Player 2" else "Player 1"
                            }
                        },
                        modifier = Modifier.padding(4.dp).size(67.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(4294239435)
                        ),
                        shape = RoundedCornerShape(8.dp) // Set the shape to rounded rectangle
                    ) { currentPlayer.value =if (currentPlayer.value=="Player 1") "Player 2" else "Player 1"
                        if (buttonStates.value[i][j] == 0) {
                            Text(
                                text = "",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = buttonStates.value[i][j].toString() ?: " ", fontSize = 26.sp,
                                fontWeight = FontWeight.Bold, color = colors[i][j],
                            )
                        }

                    }
                }
            }
        }
    }}



