package cz.chm4.chmtestapp.search.common.bl

import cz.chm4.chmtestapp.search.common.data.network.IdObject
import cz.chm4.chmtestapp.search.common.data.network.SearchEntity

fun IdObject.toSport(): Sport {
    return Sport.values()[id - 1]
}

fun IdObject.toGender(): Gender {
    return Gender.values()[id - 1]
}

fun IdObject.toEntityType(): EntityType {
    return EntityType.values()[id - 1]
}

fun SearchEntity.toSearchEntityBl(): SearchEntityBl {
    val img = images.find { it.variantTypeId == 15 }?.path

    val imgUrl = if (img != null) "${SearchEntity.BASE_IMG_URL}/$img" else null

    return SearchEntityBl(id, name, gender.toGender(), type.toEntityType(), sport.toSport(), defaultCountry.name, imgUrl)
}

