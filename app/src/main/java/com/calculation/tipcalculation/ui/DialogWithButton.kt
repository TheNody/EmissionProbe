package com.calculation.tipcalculation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.calculation.tipcalculation.db_Main.SettingsViewModel
import com.calculation.tipcalculation.utils.EXTERNAL_RESULT_SCREEN
import com.calculation.tipcalculation.utils.INTERNAL_CALCULATION_SCREEN
import com.calculation.tipcalculation.utils.INTERNAL_RESULT_SCREEN

@Composable
fun DialogWithButton (
   onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    setViewMode : SettingsViewModel,
   navControl : NavController,
    whatSeeButton : Int
){
  Dialog(onDismissRequest = { onDismissRequest() })
  {
   var whatSeeEmpty = remember { mutableStateOf(false) }
   var whatSeeOne = remember { mutableStateOf(false) }
   var whatSeeTwo = remember { mutableStateOf(false) }
   var whatSeeThree = remember {  mutableStateOf(false) }

      Card  (
          modifier = Modifier
              .fillMaxWidth()
              .height(220.dp)
              .padding(horizontal = 20.dp),
          shape = RoundedCornerShape(18.dp),
      ) {

          Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
          ) {




              when(whatSeeButton){
                  0 ->  { whatSeeEmpty.value = true }
                  1 -> whatSeeOne.value = true
                  2 -> {whatSeeTwo.value = true }
                  3 -> {whatSeeThree.value = true }
              }

              if(whatSeeEmpty.value){

                 Text(text = "Вы не ввели ни одних данных для вычислений",
                       fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
                  modifier = Modifier.padding(10.dp))


              }


              if(whatSeeOne.value)
              {

                           Text(text = "Посмотреть Internal результаты ",
                                   fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
                  modifier = Modifier.padding(10.dp))

                   Button(onClick = {

                       navControl.navigate(INTERNAL_RESULT_SCREEN)
                  //     ResultScreen( settingsViewModel =  setViewMode, 1)
                      // rememberNavController(nva) //INTERNAL_CALCULATION_SCREEN

                   }) {
                  Text(text = "Результатам Internal")
              }

              }

              if(whatSeeThree.value){

                                  Text(text = "Посмотреть EXternal результаты ",
                                   fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
                  modifier = Modifier.padding(10.dp))

                   Button(onClick = {

                       navControl.navigate(EXTERNAL_RESULT_SCREEN)
                  //     ResultScreen( settingsViewModel =  setViewMode, 1)
                      // rememberNavController(nva) //INTERNAL_CALCULATION_SCREEN

                   }) {
                  Text(text = "Результатам EXternal ")
              }

              }


              if(whatSeeTwo.value){

                   Text(text = "Выберите какие результаты хотите посмотреть",
                                   fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
                  modifier = Modifier.padding(10.dp))

                   Button(onClick = {

                       navControl.navigate(INTERNAL_RESULT_SCREEN)
                  //     ResultScreen( settingsViewModel =  setViewMode, 1)
                      // rememberNavController(nva) //INTERNAL_CALCULATION_SCREEN

                   }) {
                  Text(text = "Перейти к результатам Internal ")
              }


                  Spacer(modifier = Modifier
                      .padding(top = 20.dp))

               Button(onClick = {
                 navControl.navigate(EXTERNAL_RESULT_SCREEN)
              }) {
                  Text(text = "Перейти к результатам External")
              }
              }





          }
      }
  }
}