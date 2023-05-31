"use client";
import { useState, SyntheticEvent } from "react";
import type { User } from "@prisma/client";
import { useRouter } from "next/navigation";
import axios from "axios";

const AddProfile = ({users}: {users : User[]}) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [name, setName] = useState("");
    const [nim, setNim] = useState(""); 
    const [picture, setPicture] = useState(""); 
    const [user, setUser] = useState(""); 

    const [isOpen, setIsOpen] = useState(false);
    const router = useRouter();

    const handleSubmit = async (e: SyntheticEvent) => {
        e.preventDefault();


        await axios.post('/api/admin', {
            email: email,
            password: password,
            name: name,
            nim: nim,
            picture: picture,
            userId: Number(user)
        })
        setEmail("");
        setPassword("");
        setName("");
        setNim("");
        setPicture("");
        setUser("");
        router.refresh();
        setIsOpen(false);
    }

    const handleModal = () => {
        setIsOpen(!isOpen);
    }

    return (
    <div>
        <button className="btn" onClick={handleModal}>Add New User</button>

        <div className={isOpen ? 'modal modal-open' : 'modal'}>
            <div className="modal-box">
                <h3 className="font-bold text-lg">Add New User</h3>
                <form onSubmit={handleSubmit}>
                    <div className="form-control w-full">
                        <label className="label font-bold">Email</label>
                        <input 
                        type="text"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Email"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Password</label>
                        <input 
                        type="password" 
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Password"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Full Name</label>
                        <input 
                        type="text" 
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Full Name"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">NIM</label>
                        <input 
                        type="text" 
                        value={nim}
                        onChange={(e) => setNim(e.target.value)}
                        className="input input-bordered" 
                        placeholder="NIM"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Profile Picture</label>
                        <input 
                        type="file" 
                        value={picture}
                        onChange={(e) => setPicture(e.target.value)}
                        className="input input-bordered" 
                        placeholder="Picture"/>
                    </div>
                    <div className="form-control w-full">
                        <label className="label font-bold">Role</label>
                        <select 
                        value={user}
                        onChange={(e) => setUser(e.target.value)}
                        className="select select-bordered">
                        <option value="" disabled>Select Role</option>
                        {users.map((user) => (
                            <option value={user.id} key={user.id}>{user.role}</option>
                        ))}
                        </select>
                    </div>
                    <div className="modal-action">
                        <button type="button" className="btn" onClick={handleModal}>Close</button>
                        <button type="submit" className="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
  )
}

export default AddProfile