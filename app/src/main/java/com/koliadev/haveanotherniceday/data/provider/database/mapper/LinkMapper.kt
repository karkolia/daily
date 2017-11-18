package com.koliadev.haveanotherniceday.data.provider.database.mapper

import com.koliadev.haveanotherniceday.data.model.Category
import com.koliadev.haveanotherniceday.data.model.Link
import com.koliadev.haveanotherniceday.data.provider.database.dto.LinkDto

/**
 * Created on 18/11/2017.
 */
class LinkMapper {
  fun map(link: Link) = LinkDto(link.name, link.url, link.category.name)
  fun map(dto: LinkDto) = Link(dto.name, dto.url, Category.parse(dto.category))
}