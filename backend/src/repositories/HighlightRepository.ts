import { prisma } from '../lib/prisma'
import { Highlight } from '@prisma/client'

class Highlights {
  async createHighlight(
    CardId: number,
    color: string,
    startPosition: number,
    endPosition: number,
  ) {
    const newHighlight = await prisma.highlight.create({
      data: {
        color,
        startPosition,
        endPosition,
        Card: {
          connect: { id: CardId },
        },
      },
    })

    return newHighlight
  }

  async updateHighlight(
    highlightId: number,
    color: string,
    startPosition: number,
    endPosition: number,
  ) {
    const updated = await prisma.highlight.update({
      where: {
        id: highlightId,
      },
      data: {
        color,
        startPosition,
        endPosition,
      },
    })

    return updated
  }

  async getAllHighlights(cardId: number) {
    const allHighlights = await prisma.highlight.findMany({
      where: {
        Card: {
          id: cardId,
        },
      },
    })
    return allHighlights
  }
}

export default new Highlights()
