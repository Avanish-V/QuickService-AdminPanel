package Screens.Offers

import Network.offers.data.OfferDataModel

interface CreateOfferRepository {

    fun createOfferForAllProduct() : OfferDataModel

    fun createOfferForCategory() : OfferDataModel

    fun createOfferForSelectedUsers() : OfferDataModel

}