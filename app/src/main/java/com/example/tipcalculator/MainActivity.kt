package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipTimeScreen()
                }
            }
        }
    }
}

@Composable
fun TipTimeScreen()
{

    var amountInput by remember { mutableStateOf("")}
    var tipInput by remember {
        mutableStateOf("")
    }
    var roundUp by remember {
        mutableStateOf(false)
    }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull()?: 0.0

    val tip= calculateTip(amount, tipPercent,roundUp)


    Column(modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(text = stringResource(id = R.string.cal_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        EditNumberField(label = R.string.bill_amount,
            amountInput,
            keyboardOptions=KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            onValueChange = {amountInput=it})

        EditNumberField(label = R.string.service, value = tipInput,
            keyboardOptions=KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),onValueChange = {tipInput=it})
        
        RoundTheTipRow(roundUp = roundUp, onRoundUp = {roundUp = it})
        
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = stringResource(id = R.string.tip_amount, tip),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp, fontWeight = FontWeight.Bold
        )


    }
}
private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp:Boolean): String{
    var tip = tipPercent/100*amount
    if(roundUp)
        tip = kotlin.math.ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)




}


@Composable
fun RoundTheTipRow( roundUp: Boolean, onRoundUp: (Boolean) -> Unit, modifier: Modifier=Modifier){
    Row(modifier= Modifier
        .fillMaxWidth()
        .size(48.dp), verticalAlignment = Alignment.CenterVertically) {
        
        Text(text = stringResource(id = R.string.round_up))
        Switch(checked = roundUp,
            onCheckedChange = onRoundUp,
            Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End))
        
    }
}

@Composable
fun EditNumberField(
                    @StringRes label: Int,
                    value: String,
                    keyboardOptions: KeyboardOptions,
                    onValueChange: (String) -> Unit,
                    modifier: Modifier = Modifier
                     ){


    
    TextField(value = value,
        onValueChange = onValueChange,
        label = {
                    Text(text = stringResource(id = label),
                    modifier = Modifier.fillMaxWidth())
                },
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun defaultPreview()
{
    TipCalculatorTheme {
        TipTimeScreen()
    }
}