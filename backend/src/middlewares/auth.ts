import { NextFunction, Request, Response } from 'express'
import { verify } from 'jsonwebtoken'

type TokenPayload = {
  id: string
  iat: number
  exp: number
}

export function AuthMiddleware(
  req: Request,
  res: Response,
  next: NextFunction,
) {
  const { authorization } = req.headers
  if (!authorization) {
    return res.status(401).json({ error: 'Token not provided.' })
  }

  const [, token] = authorization.split(' ')
  console.log(authorization)

  try {
    const SECRET_KEY = process.env.SECRET_KEY
    console.log('oi')
    if (!SECRET_KEY) {
      throw new Error('Secret key not provided')
    }
    console.log(token)
    const decoded = verify(token, SECRET_KEY)
    const { id } = decoded as TokenPayload

    req.userId = id
    next()
  } catch (error) {
    return res.status(401).json({ error: 'Invalid token.' })
  }
}
