package com.koliadev.haveanotherniceday

import com.koliadev.haveanotherniceday.data.provider.urlparse.UrlParsingProvider
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UrlParsingProviderTest {
  @Test
  fun parseArchiveUrlTest() {
    val provider = UrlParsingProvider()
    provider.parse("http://geexy.fr/").map { System.out.println(it) }
  }
}
