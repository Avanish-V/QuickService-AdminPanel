package Screens.Offers

import Network.offers.data.OfferDataModel
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.swing.text.View

class createOfferViewModel : CreateOfferRepository , ViewModel() {

    override fun createOfferForAllProduct(): OfferDataModel {
        TODO("Not yet implemented")
    }


    override fun createOfferForCategory(): OfferDataModel {
        TODO("Not yet implemented")
    }

    override fun createOfferForSelectedUsers(): OfferDataModel {
        TODO("Not yet implemented")
    }

    private val _productTAG : MutableState<String> = mutableStateOf("")
    val productTAG : State<String> = _productTAG

    private val _productTitle : MutableState<String> = mutableStateOf("")
    val productTitle : State<String> = _productTitle

    private val _productId : MutableState<String> = mutableStateOf("")
    val productId : State<String> = _productId

    private val _imageUrl : MutableState<String> = mutableStateOf("")
    val imageUrl : State<String> = _imageUrl

    private val _discount : MutableState<String> = mutableStateOf("")
    val discount : State<String> = _discount

    private val _discountType : MutableState<String> = mutableStateOf("")
    val discountType : State<String> = _discountType

    private val _workType : MutableState<String> = mutableStateOf("")
    val workType : State<String> = _workType

    private val _userType : MutableState<String> = mutableStateOf("")
    val userType : State<String> = _userType

    private val _promoCode : MutableState<String> = mutableStateOf("")
    val promoCode : State<String> = _promoCode

    private val _expiration : MutableState<String> = mutableStateOf("")
    val expiration : State<String> = _expiration

    private val _appliesTo : MutableState<String> = mutableStateOf("")
    val appliesTo : State<String> = _appliesTo

    private val _status : MutableState<Boolean> = mutableStateOf(false)
    val status : State<Boolean> = _status

    fun setProductTAG(productTAG:String){
        _productTAG.value = productTAG
    }
    fun setProductTitle(productTitle:String){
        _productTitle.value = productTitle
    }
    fun setProductId(productId:String){
        _productId.value = productId
    }
    fun setImageUrl(imageUrl:String){
        _imageUrl.value = imageUrl
    }
    fun setDiscount(discount:String){
        _discount.value = discount
    }
    fun setDiscountType(discountType:String){
        _discountType.value = discountType
    }
    fun setWorkType(workType:String){
        _workType.value = workType
    }
    fun setUserType(userType:String){
        _userType.value = userType
    }
    fun setPromoCode(promoCode:String){
        _promoCode.value = promoCode
    }
    fun setExpiration(expiration:String){
        _expiration.value = expiration
    }
    fun setAppliesTo(appliesTo: String){
        _appliesTo.value = appliesTo
    }
    fun setStatus(status:Boolean){
        _status.value = status
    }
}