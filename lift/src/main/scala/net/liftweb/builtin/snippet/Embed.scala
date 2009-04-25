/*
 * Copyright 2007-2009 WorldWide Conferencing, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package net.liftweb.builtin.snippet

import scala.xml._
import net.liftweb.http._
import net.liftweb.util._
import Helpers._
import Box._

object Embed extends DispatchSnippet {

  def dispatch : DispatchIt = {
    case _ => render _
  }

  def render(kids: NodeSeq) : NodeSeq = {
    (for (ctx <- S.session) yield {
      S.attr ~ ("what") map (what => ctx.findTemplate(what.text) match {
            case Full(s) => ctx.processSurroundAndInclude(what.text, s)
            case _ => Comment("FIXME: Unable to find template named "+what) ++ kids
          }) openOr Comment("FIXME: No named specified for embedding") ++ kids

    }) openOr Comment("FIXME: session is invalid")
  }

}