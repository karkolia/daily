package com.koliadev.haveanotherniceday.data.provider.urlparse

import com.koliadev.haveanotherniceday.utils.isEmptyOrNull
import com.koliadev.haveanotherniceday.utils.log
import org.w3c.dom.DOMException
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.SAXException
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class UrlParsingProvider {

  fun parse(url: String): ArrayList<String> {
    var imgUrls: ArrayList<String>? = parseFeed(url)

    if (isEmptyOrNull(imgUrls)) {
      log("Feed parsing failed. Trying custom parse... Please wait.")
      imgUrls = parseArchivesPage(url)
    }
    return if (imgUrls == null) ArrayList() else imgUrls.mapTo(ArrayList()) { prepareImgUrl(it, url) }
  }

  private fun prepareImgUrl(imgUrl: String, inUrl: String): String {
    var prepared = imgUrl
    if (imgUrl.indexOf(".") == imgUrl.lastIndexOf(".")) {
      var part1 = inUrl
      var part2 = imgUrl

      val ind = part1.indexOf("?")
      if (ind > 0) {
        part1 = part1.substring(0, ind)
        val j = part1.lastIndexOf("/")
        if (j > 0)
          part1 = part1.substring(0, j)
      }

      if (inUrl.endsWith("/") && imgUrl.startsWith("/")) {
        part2 = part2.substring(1)
      } else if (!inUrl.endsWith("/") && !imgUrl.startsWith("/")) {
        part2 = "/" + part2
      }
      prepared = part1 + part2
    }
    return prepared
  }

  private fun parseArchivesPage(archivesUrl: String): ArrayList<String>? {
    var imgUrls: ArrayList<String>? = null

    try {
      val url = URL(archivesUrl)
      val urlC = url.openConnection()
      val `is` = urlC.getInputStream()

      val sc = Scanner(`is`)
      var content = ""

      while (sc.hasNext()) {
        content += sc.nextLine()
      }

      imgUrls = getAllImagesSources(content)
    } catch (ex: IOException) {
      log(exception = ex)
    }

    return imgUrls
  }

  private fun getAllImagesSources(content: String): ArrayList<String> {
    val tmp = eliminateUnNeededStart(content)
    var imagesContents: ArrayList<String>? = getAllImagesSrcShorter(tmp)

    if (imagesContents == null || imagesContents.size == 0) {
      imagesContents = getAllImagesSrcShorterMethod2(tmp)
    }
    return imagesContents
  }

  private fun getAllImagesSrcShorter(tmp: String): ArrayList<String> {
    val parts = tmp.split("src=\"".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

    return parts
        .map { s -> s.split("\"".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0] }
        .filterTo(ArrayList()) { !isEmptyOrNull(it) && validateSrc(it) }
  }

  private fun getAllImagesSrcShorterMethod2(rest: String): ArrayList<String> {
    val parts = rest.split("<enclosure url=\"".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

    return parts
        .map { s -> s.split("\"".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0] }
        .filterTo(ArrayList()) { !isEmptyOrNull(it) && validateSrc(it) }
  }

  private fun eliminateUnNeededStart(content: String): String {
    var res = content
    val i = res.indexOf("id=\"content\"")
    if (i >= 0) {
      res = res.substring(i + "id=\"content\"".length)
    }
    return res
  }

  private fun validateSrc(source: String): Boolean {
    val s = source.replace("\\s".toRegex(), "")
    return !(s.contains("small_white_checkmark.png")
        || s.contains("/tumblr.png") || s.contains("/email.png")
        || s.contains("//maps.google.com/maps/api")
        || s.contains("//www.facebook.com/plugins") || s.contains("<")
        || s.contains(">")
        || !(s.endsWith(".jpg") || s.endsWith(".png")))
  }

  private fun parseFeed(feedUrl: String): ArrayList<String> {
    val imgUrls = ArrayList<String>()

    try {
      val builder = DocumentBuilderFactory.newInstance()
          .newDocumentBuilder()
      val url = URL(feedUrl)
      val urlC = url.openConnection()
      val inputStream = urlC.getInputStream()

      val doc = builder.parse(inputStream)
      val nodes = doc.getElementsByTagName("item")
      var element: Element?

      for (i in 0 until nodes!!.length) {
        element = nodes.item(i) as Element

        val imgUrl = getImgUrl(element)
        if (imgUrl != null)
          imgUrls.add(imgUrl)
      }

    } catch (ex: SAXException) {
      log(ex)
    } catch (ex: IOException) {
      log(ex)
    } catch (ex: ParserConfigurationException) {
      log(ex)
    } catch (ex: DOMException) {
      log(ex)
    }

    return imgUrls
  }

  private fun getChildNodeContent(node: Node?, childName: String): String? {
    val childNode = getChildByName(node, childName)
    if (childNode != null) {
      if (childNode.nodeValue != null)
        return childNode.nodeValue
      else if (childNode.firstChild != null)
        return childNode.firstChild.nodeValue
    }
    return null
  }

  private fun getImgUrl(node: Node?): String? {
    var descriptionContent = getChildNodeContent(node, "description")
    if (descriptionContent == null)
      descriptionContent = getChildNodeContent(node, "link")

    if (descriptionContent != null) {
      var s = descriptionContent.replace("/>".toRegex(), "<-close->")
      s = s.replace("&gt;".toRegex(), "<-close->")
      s = s.replace("<img ".toRegex(), "<-imgOpen->")
      s = s.replace("&lt;img".toRegex(), "<-imgOpen->")

      val i = s.indexOf("<-imgOpen->")

      if (i >= 0)
        s = s.substring(i)

      val j = s.indexOf("<-close->")

      if (j >= 0)
        s = s.substring(0, j)

      val p = Pattern.compile("<-imgOpen->(.*)", Pattern.DOTALL)
      val matcher = p.matcher(s)

      if (matcher.matches()) {
        var match = matcher.group(1)

        match = match.replace("src=\"".toRegex(), "<-imgSrcOpen->")
        match = match.replace("\"".toRegex(), "<-close->")

        val k = match.indexOf("<-imgSrcOpen->")

        if (k >= 0)
          match = match.substring(k)

        val l = match.indexOf("<-close->")

        if (l >= 0)
          match = match.substring(0, l)

        val p2 = Pattern.compile("<-imgSrcOpen->(.*)",
            Pattern.DOTALL)
        val matcher2 = p2.matcher(match)

        if (matcher2.matches()) {
          return matcher2.group(1)
        }
      }
    }
    return null
  }

  private fun getChildByName(node: Node?, name: String): Node? {
    if (node == null) {
      return null
    }
    val listChild = node.childNodes

    if (listChild != null) {
      for (i in 0 until listChild.length) {
        val child = listChild.item(i)
        if (child != null) {
          if (child.nodeName != null && name == child
              .nodeName || child.localName != null && name == child.localName) {
            return child
          }
        }
      }
    }
    return null
  }
}