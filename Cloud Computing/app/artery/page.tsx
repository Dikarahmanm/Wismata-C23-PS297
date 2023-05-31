import { getServerSession } from "next-auth"
import { authOptions } from "../api/auth/[...nextauth]/route"
import { redirect } from "next/navigation"

export default async function Artery() {

    const session = await getServerSession(authOptions)

    if (!session) {
        redirect('/api/auth/signin')
    }

    return (
        <> Artery Page </>
    )
}