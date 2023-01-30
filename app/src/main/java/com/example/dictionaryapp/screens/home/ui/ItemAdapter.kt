import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ItemAdapterBinding
import com.example.dictionaryapp.model.Item
import com.example.dictionaryapp.screens.home.ui.ItemClickListener

class ItemAdapter(
    private var items: List<Item>,
    private val context: Context,
    private val itemClickListener: ItemClickListener?
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private lateinit var itemStoreAdapterBinding: ItemAdapterBinding

    protected var mItemClickListener: ItemClickListener? = null

    private val addressId = MutableLiveData<Int>()
    val addressSelectedId: MutableLiveData<Int> = addressId

    var selectedPosition: Int = -1

    init {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemStoreAdapterBinding = ItemAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(itemStoreAdapterBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setupRbButton(holder, position)
        setupTexts(holder, position)
        if ((items.size - 1) == position) {
            itemStoreAdapterBinding.divider.visibility = View.GONE
        }
    }

    private fun setupTexts(
        holder: ViewHolder,
        position: Int
    ) {
        holder.title?.text = items[position].title
        holder.txtDescription?.text = items[position].description
    }

    private fun setupRbButton(
        holder: ViewHolder,
        position: Int
    ) {
        //holder.rbButton?.isChecked = position == selectedPosition


        holder.rbButton?.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    selectedPosition = holder.adapterPosition
                    mItemClickListener?.onClick(items[selectedPosition])
                    addressSelectedId.postValue(selectedPosition)
                }
            }
        })
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtDescription: TextView? = null
        var title: TextView? = null
        var rbButton: RadioButton? = null

        init {
            txtDescription = itemView.findViewById(R.id.idTxtDescriptionItem)
            title = itemView.findViewById(R.id.idTxtTitleItem)
            rbButton = itemView.findViewById(R.id.idRbButton)
        }
    }
}