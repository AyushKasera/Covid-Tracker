package com.example.coviodtracker


import android.os.Bundle
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException



class MainActivity : AppCompatActivity() {

    lateinit var countryCasesTv:TextView
    lateinit var countryRecoveredTv:TextView
    lateinit var countryDeathsTv:TextView
    lateinit var stateRv: RecyclerView
    lateinit var stateRvAdapter: StateRVAdapter
    lateinit var stateList:List<StateModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countryCasesTv=findViewById(R.id.TVIndiacases)
        countryDeathsTv=findViewById(R.id.TVIndiaDeaths)
        countryRecoveredTv=findViewById(R.id.TVIndiarecovered)
        stateRv=findViewById(R.id.idRVState)
        stateList= arrayListOf<StateModel>()
        getStateInfo()

    }

    private fun getStateInfo()
    {
        val url="https://api.rootnet.in/covid19-in/stats/latest"
        val queue= Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET,url, null, { response ->
            try{
                val dataObj = response.getJSONObject("data")
                val summaryObj = dataObj.getJSONObject("summary")
                val cases: Int = summaryObj.getInt("total")
                val recovered:Int = summaryObj.getInt("discharged")
                val deaths: Int = summaryObj.getInt("deaths")

                countryCasesTv.text = cases.toString()
                countryRecoveredTv.text = recovered.toString()
                countryDeathsTv.text = deaths.toString()

                val regionalArray = dataObj.getJSONArray("regional")
                for(i in 0 until regionalArray.length()){
                    val regionalObj = regionalArray.getJSONObject(i)
                    val stateName: String = regionalObj.getString("loc")
                    val cases: Int = regionalObj.getInt("totalConfirmed")
                    val deaths: Int = regionalObj.getInt("deaths")
                    val recovered: Int = regionalObj.getInt("discharged")

                    val stateCases = StateModel(stateName,recovered,deaths,cases)
                    stateList = stateList+stateCases
                }
                stateRvAdapter = StateRVAdapter(stateList)
                stateRv.layoutManager = LinearLayoutManager(this)
                stateRv.adapter = stateRvAdapter
            }
            catch (e: JSONException){
                e.printStackTrace()
            }

        },  {error -> Toast.makeText(this, "Failed to get Data", Toast.LENGTH_SHORT).show()
        }
        )
        queue.add(request)

    }


}