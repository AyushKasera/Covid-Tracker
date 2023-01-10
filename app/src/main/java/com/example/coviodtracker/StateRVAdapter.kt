package com.example.coviodtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class StateRVAdapter(private val stateList:List<StateModel>):RecyclerView.Adapter<StateRVAdapter.StateRvViewHolder> ()
{

    class StateRvViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val stateNameTv:TextView=itemView.findViewById(R.id.idTVState)
        val casesTv:TextView=itemView.findViewById(R.id.idTVcases)
        val deathsTv:TextView=itemView.findViewById(R.id.idTVDeaths)
        val recoveredTv:TextView=itemView.findViewById(R.id.idTVrecovered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRvViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return  StateRvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StateRvViewHolder, position: Int) {
        val stateDeta=stateList[position]
        holder.casesTv.text=stateDeta.cases.toString()
        holder.deathsTv.text=stateDeta.deaths.toString()
        holder.stateNameTv.text=stateDeta.state
        holder.recoveredTv.text=stateDeta.recovered.toString()
    }

    override fun getItemCount(): Int {
       return stateList.size
    }


}